package com.demo.auth.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.demo.auth.entity.User;
import com.demo.auth.jpa.UserRepository;
import com.demo.auth.util.JwtTokenUtil;
import com.demo.dto.AuthModelDto;
import com.demo.exceptions.TotpRequiredException;
import com.demo.exceptions.UserExpiredException;
import com.demo.exceptions.UserLockedException;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRolesDetailsService userRolesDetailsService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	public User loginUser(AuthModelDto authModel) throws Exception {

		authenticate(authModel.getUsername(), authModel.getPassword());

		if (Boolean.FALSE.equals(userRolesDetailsService.checkUserNonExpired(authModel.getUsername()))) {

			if (Boolean.FALSE.equals(userRolesDetailsService.checkUserNonLocked(authModel.getUsername()))) {

				final UserDetails userDetails = userRolesDetailsService.loadUserByUsername(authModel.getUsername());

				Optional<User> user = userRepository.findByUsername(userDetails.getUsername());

				if (Boolean.TRUE.equals(user.get().getMfaEnabled())) {
					throw new TotpRequiredException("Multi-Factor Authentication Required");
				} else {

					final String token = jwtTokenUtil.generateToken(userDetails);
					user.get().setAccessToken(token);
					return user.get();

				}

			} else {
				throw new UserLockedException("User Locked");
			}

		} else {
			throw new UserExpiredException("User Expired");
		}

	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("User disabled");
		} catch (BadCredentialsException e) {
			throw new Exception("Bad Credentials");
		}
	}

}


























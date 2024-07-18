package com.demo.auth.service;

import java.util.Arrays;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.demo.auth.entity.Role;
import com.demo.auth.entity.User;
import com.demo.auth.jpa.UserRepository;
import com.demo.dto.UserDto;
import com.demo.exceptions.UserExistsException;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User createUser(UserDto user) {
		if (Boolean.TRUE.equals(userRepository.existsByUsername(user.getUsername()))) {
			throw new UserExistsException("User is already registered with username: " + user.getUsername());
		}
		User newUser = new User();
		BeanUtils.copyProperties(user, newUser);
		newUser.setPassword(bcryptEncoder.encode(newUser.getPassword()));
		newUser.setCredentialsNonExpired(Boolean.TRUE);
		newUser.setAccountNonExpired(Boolean.TRUE);
		newUser.setAccountNonLocked(Boolean.TRUE);
		newUser.setEnabled(Boolean.TRUE);
		newUser.setMfaEnabled(Boolean.FALSE);
		newUser.setRoles(Arrays.asList(new Role(3l, "ROLE_USER")));
		return userRepository.save(newUser);
	}

	@Override
	public User readUser() {
		Long userId = getLoggedInUser().getId();
		return userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found for the id: " +userId));
	}

	@Override
	public User updateUser(UserDto user) {
		User existingUser = readUser();
		existingUser.setFirstName(user.getFirstName() != null ? user.getFirstName() : existingUser.getFirstName());
		existingUser.setSecondName(user.getSecondName() != null ? user.getSecondName() : existingUser.getSecondName());
		existingUser.setUsername(user.getUsername() != null ? user.getUsername() : existingUser.getUsername());
		existingUser.setPassword(user.getPassword() != null ? bcryptEncoder.encode(user.getPassword()) : existingUser.getPassword());
		return userRepository.save(existingUser);
	}

	@Override
	public void deleteUser() {
		User existingUser = readUser();
		userRepository.delete(existingUser);
	}

	@Override
	public User getLoggedInUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found for the username: " + username));
	}

}


























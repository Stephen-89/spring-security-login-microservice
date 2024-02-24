package com.demo.auth.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.demo.auth.jpa.UserRepository;
import com.demo.auth.entity.User;
import com.demo.auth.entity.Role;


@Service
public class UserRolesDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByUsername(username);
		if (!user.isPresent()) {
			throw new UsernameNotFoundException("User not found"); 
		} else {
			return new org.springframework.security.core.userdetails.User(user.get().getUsername(),
					user.get().getPassword(), user.get().getEnabled(), true, true, true,
					getAuthorities(user.get().getRoles()));
		}
	}

    private Collection<? extends GrantedAuthority> getAuthorities(List<Role> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }
    
	public Boolean checkUserNonExpired(String email) throws UsernameNotFoundException {
		return userRepository
				.existsByUsernameAndAccountNonExpired(email, Boolean.FALSE);
	}

	public Boolean checkUserNonLocked(String email) throws UsernameNotFoundException {
		return userRepository
				.existsByUsernameAndAccountNonLocked(email, Boolean.FALSE);
	}

}
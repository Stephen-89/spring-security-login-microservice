package com.demo.auth.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.auth.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	Boolean existsByUsername(String username);
	
	Optional<User> findByUsername(String username);
	
	Boolean existsByUsernameAndAccountNonExpired(String username, Boolean expired);
	
	Boolean existsByUsernameAndAccountNonLocked(String username, Boolean locked);
	
}

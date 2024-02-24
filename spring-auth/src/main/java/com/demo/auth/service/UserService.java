package com.demo.auth.service;

import com.demo.auth.entity.User;
import com.demo.dto.UserDto;

public interface UserService {
	
	User createUser(UserDto user);
	
	User readUser();
	
	User updateUser(UserDto user);
	
	void deleteUser();
	
	User getLoggedInUser();
	
}

package com.demo.auth.service;

import org.springframework.http.ResponseEntity;

import com.demo.auth.entity.User;
import com.demo.dto.AuthModelDto;

public interface AuthService {

	ResponseEntity<User> loginUser(AuthModelDto authModel) throws Exception;

}

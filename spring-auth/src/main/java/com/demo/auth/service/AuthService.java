package com.demo.auth.service;

import com.demo.auth.entity.User;
import com.demo.dto.AuthModelDto;

public interface AuthService {

	User loginUser(AuthModelDto authModel) throws Exception;

}

package com.demo.auth.util;

import com.demo.auth.entity.User;
import com.demo.dto.UserDto;

public final class EntityToDtoMapper {

	private EntityToDtoMapper() {
	}

	public static UserDto userToUserDto(User user) {
		return new UserDto(user.getFirstName(), user.getSecondName(), user.getUsername());
	}

}

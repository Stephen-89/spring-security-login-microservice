package com.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class UserLockedException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public UserLockedException(String message) {
		super(message);
	}

}

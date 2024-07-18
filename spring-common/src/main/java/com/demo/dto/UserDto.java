package com.demo.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
	
	public UserDto(String firstName, String secondName, String username) {
		this.firstName = firstName;
		this.secondName = secondName;
		this.username = username;
	}

	@NotBlank(message = "First Name should not be empty")
	private String firstName;

	@NotBlank(message = "Second Name should not be empty")
	private String secondName;

	@NotNull(message = "Username should not be empty")
	@Email(message = "Enter a valid username")
	private String username;
	
	@JsonIgnore
	@NotNull(message = "Password should not be empty")
	@Size(min = 5, message = "Password should be atleast 5 characters")
	private String password;
	
	@JsonIgnore
	private boolean accountNonExpired;
	
	@JsonIgnore
	private boolean accountNonLocked;
	
	@JsonIgnore
	private boolean credentialsNonExpired;
	
	@JsonIgnore
	private boolean enabled;
	
	@JsonIgnore
	private String code;
	
}

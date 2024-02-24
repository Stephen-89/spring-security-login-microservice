package com.demo.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ErrorDto {
	
	private Integer statusCode;
	private String message;
	private Date timestamp;
	
}

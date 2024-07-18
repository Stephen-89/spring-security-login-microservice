package com.demo.auth.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.auth.service.AuthService;
import com.demo.auth.service.TotpService;
import com.demo.auth.util.EntityToDtoMapper;
import com.demo.dto.AuthModelDto;
import com.demo.dto.ImageDto;
import com.demo.dto.MfaDto;
import com.demo.dto.UserDto;

@RestController
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private TotpService totpService;
	
	@PostMapping("/login")
	public UserDto login(@RequestBody AuthModelDto authModel) throws Exception {
		return EntityToDtoMapper.userToUserDto(authService.loginUser(authModel));
	}

	@GetMapping("/generate-qr-image")
	public ResponseEntity<ImageDto> generateUriForImage() {
		return new ResponseEntity<>(new ImageDto(totpService.generateUriForImage()), HttpStatus.CREATED);
	}

	@PostMapping("/disable-mfa")
	public void disableMfa() {
		totpService.disableMfa();
	}

	@PostMapping("/verfiy-code")
	public ResponseEntity<Object> verifyCode(@Valid @RequestBody MfaDto mfaDto) {
		return new ResponseEntity<>(totpService.verifyCode(mfaDto), HttpStatus.OK);
	}
	
}



















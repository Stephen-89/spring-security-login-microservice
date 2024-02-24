package com.demo.auth.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.auth.entity.User;
import com.demo.auth.service.AuthService;
import com.demo.auth.service.TotpService;
import com.demo.dto.AuthModelDto;
import com.demo.dto.ImageDto;
import com.demo.dto.MfaDto;

@RestController
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private TotpService totpService;
	
	@PostMapping("/login")
	public ResponseEntity<User> login(@RequestBody AuthModelDto authModel) throws Exception {
		return authService.loginUser(authModel);
	}

	@GetMapping("/generate-qr-image")
	public ResponseEntity<ImageDto> generateUriForImage() {
		return new ResponseEntity<ImageDto>(new ImageDto(totpService.generateUriForImage()), HttpStatus.CREATED);
	}

	@PostMapping("/disable-mfa")
	public void disableMfa() {
		totpService.disableMfa();
	}

	@PostMapping("/verfiy-code")
	public ResponseEntity<Object> verifyCode(@Valid @RequestBody MfaDto mfaDto) {
		return new ResponseEntity<Object>(totpService.verifyCode(mfaDto), HttpStatus.OK);
	}
	
}



















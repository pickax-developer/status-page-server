package com.pickax.status.page.server.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pickax.status.page.server.dto.request.UserResignRequestDto;
import com.pickax.status.page.server.dto.request.auth.EmailAuthRequestDto;
import com.pickax.status.page.server.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
	private final AuthService authService;

	@PostMapping("/email-auth")
	public void authenticateEmailForSignup(@RequestBody @Valid EmailAuthRequestDto emailAuthRequestDto) {
		this.authService.authenticateEmailForSignup(emailAuthRequestDto);
	}

	@PostMapping("/resign")
	public ResponseEntity<Void> resign(UserResignRequestDto userResignRequestDto) {
		authService.resign(userResignRequestDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}

package com.pickax.status.page.server.controller;

import com.pickax.status.page.server.dto.request.auth.EmailAuthVerifyRequestDto;
import com.pickax.status.page.server.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pickax.status.page.server.dto.request.UserResignRequestDto;
import com.pickax.status.page.server.dto.request.LoginRequestDto;
import com.pickax.status.page.server.security.dto.AccessTokenResponseDto;
import com.pickax.status.page.server.dto.request.auth.EmailAuthSendRequestDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
	private final AuthService authService;

	@PostMapping("/email-auth")
	public ResponseEntity<Void> sendEmailAuthenticationCodeForSignup(@RequestBody @Valid EmailAuthSendRequestDto emailAuthSendRequestDto) {
		this.authService.sendEmailAuthenticationCodeForSignup(emailAuthSendRequestDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/email-auth/verify")
	public ResponseEntity<Void> verifyEmailAuthenticationCodeForSignup(@RequestBody @Valid EmailAuthVerifyRequestDto emailAuthVerifyRequestDto) {
		this.authService.verifyEmailAuthenticationCodeForSignup(emailAuthVerifyRequestDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/resign")
	public ResponseEntity<Void> resign(@RequestBody @Valid UserResignRequestDto userResignRequestDto) {
		authService.resign(userResignRequestDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/login")
	public ResponseEntity<AccessTokenResponseDto> login(@RequestBody @Valid LoginRequestDto request) {
		return ResponseEntity.ok(this.authService.login(request));
	}

	@PostMapping("/logout")
	public ResponseEntity<Void> logout() {
		return new ResponseEntity<>(HttpStatus.OK);
	}
}

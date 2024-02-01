package com.pickax.status.page.server.controller;

import com.pickax.status.page.server.dto.request.auth.EmailAuthRequestDto;
import com.pickax.status.page.server.dto.request.LoginRequestDto;
import com.pickax.status.page.server.security.dto.AccessTokenResponseDto;
import com.pickax.status.page.server.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/email-auth")
    public void authenticateEmailForSignup(@RequestBody @Valid EmailAuthRequestDto emailAuthRequestDto) {
        this.authService.authenticateEmailForSignup(emailAuthRequestDto);
    }

    @PostMapping("/login")
    public ResponseEntity<AccessTokenResponseDto> login(@RequestBody @Valid LoginRequestDto request) {
        return ResponseEntity.ok(this.authService.login(request));
    }
}

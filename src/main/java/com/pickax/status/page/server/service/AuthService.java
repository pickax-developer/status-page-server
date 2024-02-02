package com.pickax.status.page.server.service;

import com.pickax.status.page.server.common.exception.CustomException;
import com.pickax.status.page.server.common.exception.ErrorCode;
import com.pickax.status.page.server.domain.enumclass.UserStatus;
import com.pickax.status.page.server.domain.model.User;
import com.pickax.status.page.server.dto.request.LoginRequestDto;
import com.pickax.status.page.server.dto.request.auth.EmailAuthRequestDto;
import com.pickax.status.page.server.repository.UserRepository;
import com.pickax.status.page.server.security.config.SecurityConfig;
import com.pickax.status.page.server.security.dto.AccessTokenResponseDto;
import com.pickax.status.page.server.security.jwt.TokenProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final EmailService emailService;
    private final TokenProvider tokenProvider;

    @Transactional
    public void authenticateEmailForSignup(EmailAuthRequestDto emailAuthRequestDto) {
        userRepository.getUser(emailAuthRequestDto.getEmail(), UserStatus.JOIN).ifPresent(user -> {
            throw new CustomException(ErrorCode.DUPLICATE_USER);
        });

        emailService.sendEmailAuthenticationCodeForSignup(emailAuthRequestDto.getEmail());
    }


    @Transactional
    public AccessTokenResponseDto login(LoginRequestDto request) {
        User user = userRepository.getUser(request.getEmail(), UserStatus.JOIN)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        verifyPassword(request.getPassword(), user.getPassword());

        return tokenProvider.createAccessToken(user.getId());
    }

    private void verifyPassword(String inputPassword, String password) {
        BCryptPasswordEncoder passwordEncoder = SecurityConfig.passwordEncoder();

        if (!passwordEncoder.matches(inputPassword, password)) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }
    }
}

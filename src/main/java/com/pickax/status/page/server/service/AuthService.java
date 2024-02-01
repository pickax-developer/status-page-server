package com.pickax.status.page.server.service;

import com.pickax.status.page.server.common.exception.CustomException;
import com.pickax.status.page.server.common.exception.ErrorCode;
import com.pickax.status.page.server.domain.enumclass.UserStatus;
import com.pickax.status.page.server.domain.model.User;
import com.pickax.status.page.server.dto.UserDto;
import com.pickax.status.page.server.dto.request.LoginRequestDto;
import com.pickax.status.page.server.dto.request.auth.EmailAuthRequestDto;
import com.pickax.status.page.server.repository.UserRepository;
import com.pickax.status.page.server.security.config.SecurityConfig;
import com.pickax.status.page.server.security.dto.AccessTokenResponseDto;
import com.pickax.status.page.server.security.jwt.TokenProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final EmailService emailService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
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
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        verifyUser(request, new UserDto(user.getEmail(), user.getPassword(), user.getStatus()));

        UsernamePasswordAuthenticationToken authenticationToken = createAuthenticationToken(request.getEmail(), request.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        return tokenProvider.createAccessToken(authentication);
    }

    private UsernamePasswordAuthenticationToken createAuthenticationToken(String email, String password) {
        return new UsernamePasswordAuthenticationToken(email, password);
    }

    private void verifyUser(LoginRequestDto request, UserDto user) {
        verifyPassword(request.getPassword(), user.getPassword());
        verifyStatus(user.getStatus());
    }

    private void verifyStatus(UserStatus status) {
        if (UserStatus.WITHDRAWAL.equals(status)) {
            throw new CustomException(ErrorCode.INVALID_USER_STATUS);
        }

    }

    // 비밀번호가 어떻게 저장되어 있는지 알 수가 없어서 BCryptPasswordEncoder 를 사용해 비밀번호를 비교합니다.
    private void verifyPassword(String inputPassword, String password) {
        BCryptPasswordEncoder passwordEncoder = SecurityConfig.passwordEncoder();

        if (!passwordEncoder.matches(inputPassword, password)) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }
    }
}

package com.pickax.status.page.server.service;

import com.pickax.status.page.server.common.exception.CustomException;
import com.pickax.status.page.server.common.exception.ErrorCode;
import com.pickax.status.page.server.domain.enumclass.UserStatus;
import com.pickax.status.page.server.dto.request.auth.EmailAuthRequestDto;
import com.pickax.status.page.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final EmailService emailService;

    @Transactional
    public void authenticateEmailForSignup(EmailAuthRequestDto emailAuthRequestDto) {
        userRepository.getUser(emailAuthRequestDto.getEmail(), UserStatus.JOIN).ifPresent(user -> {
            throw new CustomException(ErrorCode.DUPLICATE_USER);
        });

        emailService.sendEmailAuthenticationCodeForSignup(emailAuthRequestDto.getEmail());
    }
}

package com.pickax.status.page.server.service;

import static com.pickax.status.page.server.common.exception.ErrorCode.*;

import java.util.List;

import com.pickax.status.page.server.dto.request.auth.SignupRequestDto;
import com.pickax.status.page.server.dto.request.auth.EmailAuthVerifyRequestDto;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pickax.status.page.server.common.event.UserResignEvent;
import com.pickax.status.page.server.common.exception.CustomException;
import com.pickax.status.page.server.common.exception.ErrorCode;
import com.pickax.status.page.server.domain.enumclass.UserStatus;
import com.pickax.status.page.server.domain.model.Component;
import com.pickax.status.page.server.domain.model.Site;
import com.pickax.status.page.server.domain.model.User;
import com.pickax.status.page.server.dto.request.UserResignRequestDto;
import com.pickax.status.page.server.domain.model.EmailAuthentication;
import com.pickax.status.page.server.dto.request.LoginRequestDto;
import com.pickax.status.page.server.dto.request.auth.EmailAuthSendRequestDto;
import com.pickax.status.page.server.repository.EmailAuthenticationRepository;
import com.pickax.status.page.server.repository.SiteRepository;
import com.pickax.status.page.server.repository.UserRepository;

import com.pickax.status.page.server.security.dto.AccessTokenResponseDto;
import com.pickax.status.page.server.security.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
	private final UserRepository userRepository;
	private final SiteRepository siteRepository;
	private final EmailAuthenticationRepository emailAuthenticationRepository;

	private final EmailService emailService;

    private final ApplicationEventPublisher eventPublisher;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

	@Transactional
	public void sendEmailAuthenticationCodeForSignup(EmailAuthSendRequestDto emailAuthSendRequestDto) {
		String requestEmail = emailAuthSendRequestDto.getEmail();
		checkDuplicatedUser(requestEmail);
		cleanAllEmailAuthenticationByEmail(requestEmail);
		String code = RandomStringUtils.randomNumeric(6);
		emailAuthenticationRepository.save(EmailAuthentication.create(requestEmail, code, LocalDateTime.now().plusMinutes(10)));
		emailService.sendEmailAuthenticationCodeForSignup(requestEmail, code);
	}

	private void checkDuplicatedUser(String email) {
		userRepository.getUser(email, UserStatus.JOIN).ifPresent(user -> {
			throw new CustomException(ErrorCode.DUPLICATE_USER);
		});
	}

	private void cleanAllEmailAuthenticationByEmail(String email) {
		List<EmailAuthentication> emailAuthentications = emailAuthenticationRepository.findAllByEmail(email);
		if (!emailAuthentications.isEmpty()) {
			emailAuthenticationRepository.deleteAll(emailAuthentications);
		}
	}

	@Transactional
	public void resign(UserResignRequestDto userResignRequestDto) {
		// TODO SecurityContextHolder 완료되면 추가
		// Long userId = SecurityUtil.getCurrentUserId();
		Long userId = 1L;
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new CustomException(NOT_FOUND_USER));

		if (!passwordEncoder.matches(userResignRequestDto.password(), user.getPassword())) {
			throw new CustomException(INVALID_PASSWORD);
		}

		List<Site> sites = siteRepository.findByUserId(userId);

		eventPublisher.publishEvent(new UserResignEvent(user.getEmail()));

		delete(user, sites);
	}

	private void delete(User user, List<Site> sites) {
		deleteUser(user);
		sites.forEach(this::cancelSiteAndDeactivateComponents);
	}

	private void deleteUser(User user) {
		user.delete();
	}

	private void cancelSiteAndDeactivateComponents(Site site) {
		site.cancel();
		site.getComponents().forEach(Component::deactivate);
	}

	@Transactional(readOnly = true)
	public void verifyEmailAuthenticationCodeForSignup(EmailAuthVerifyRequestDto emailAuthVerifyRequestDto) {
		verifyEmailAuthenticationCodeForSignup(emailAuthVerifyRequestDto.getEmail(), emailAuthVerifyRequestDto.getCode());
	}

	private void verifyEmailAuthenticationCodeForSignup(String email, String code) {
		EmailAuthentication emailAuthentication = emailAuthenticationRepository.findFirst1ByEmail(email)
				.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_AUTHENTICATION_CODE));

		if (!emailAuthentication.verify(code)) {
			throw new CustomException(ErrorCode.INVALID_AUTHENTICATION_CODE);
		}

		if (emailAuthentication.isExpired()) {
			throw new CustomException(ErrorCode.NOT_FOUND_AUTHENTICATION_CODE);
		}
	}

    @Transactional
    public AccessTokenResponseDto login(LoginRequestDto request) {
        User user = userRepository.getUser(request.getEmail(), UserStatus.JOIN)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        verifyPassword(request.getPassword(), user.getPassword());

        return tokenProvider.createAccessToken(user.getId());
    }

    private void verifyPassword(String inputPassword, String password) {
        if (!passwordEncoder.matches(inputPassword, password)) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }
    }

	@Transactional
	public void signup(SignupRequestDto signupRequestDto) {
		String email = signupRequestDto.getEmail();
		checkDuplicatedUser(email);
		verifyEmailAuthenticationCodeForSignup(email, signupRequestDto.getCode());
		userRepository.save(User.create(email, passwordEncoder.encode(signupRequestDto.getPassword())));

		emailService.sendSignupEmail(email);
		cleanAllEmailAuthenticationByEmail(email);
	}
}

package com.pickax.status.page.server.service;

import static com.pickax.status.page.server.common.exception.ErrorCode.*;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pickax.status.page.server.common.exception.CustomException;
import com.pickax.status.page.server.common.exception.ErrorCode;
import com.pickax.status.page.server.domain.enumclass.UserStatus;
import com.pickax.status.page.server.domain.model.Component;
import com.pickax.status.page.server.domain.model.Site;
import com.pickax.status.page.server.domain.model.User;
import com.pickax.status.page.server.dto.request.UserResignRequestDto;
import com.pickax.status.page.server.dto.request.auth.EmailAuthRequestDto;
import com.pickax.status.page.server.repository.SiteRepository;
import com.pickax.status.page.server.repository.UserRepository;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final SiteRepository siteRepository;
	private final EmailService emailService;

	@Transactional
	public void authenticateEmailForSignup(EmailAuthRequestDto emailAuthRequestDto) {
		userRepository.getUser(emailAuthRequestDto.getEmail(), UserStatus.JOIN).ifPresent(user -> {
			throw new CustomException(ErrorCode.DUPLICATE_USER);
		});

		emailService.sendEmailAuthenticationCodeForSignup(emailAuthRequestDto.getEmail());
	}

	public void resign(UserResignRequestDto userResignRequestDto) {
		// TODO SecurityContextHolder 완료되면 추가
		// Long userId = SecurityUtil.getCurrentUserId();
		Long userId = 1L;
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new CustomException(NOT_FOUND_USER));

		if (!passwordEncoder.matches(userResignRequestDto.password(), user.getPassword())) {
			throw new CustomException(INVALID_INPUT_VALUE);
		}

		Site site = siteRepository.findByUserId(userId);

		delete(user, site);
		// TODO 회원탈퇴 메일 전송
		// eventPublisher.publishEvent(UserResignEvent);
	}

	private void delete(User user, Site site) {
		user.delete();
		site.delete();
		for (Component component : site.getComponents()) {
			component.delete();
		}
	}
}

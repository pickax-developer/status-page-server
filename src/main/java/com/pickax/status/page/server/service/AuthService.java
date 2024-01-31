package com.pickax.status.page.server.service;

import static com.pickax.status.page.server.common.exception.ErrorCode.*;

import java.util.List;

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
import com.pickax.status.page.server.dto.request.auth.EmailAuthRequestDto;
import com.pickax.status.page.server.repository.EmailAuthenticationRepository;
import com.pickax.status.page.server.repository.SiteRepository;
import com.pickax.status.page.server.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {
	private final UserRepository userRepository;
	private final SiteRepository siteRepository;
	private final EmailAuthenticationRepository emailAuthenticationRepository;

	private final EmailService emailService;

	private final ApplicationEventPublisher eventPublisher;

	private final PasswordEncoder passwordEncoder;

	@Transactional
	public void authenticateEmailForSignup(EmailAuthRequestDto emailAuthRequestDto) {
		String requestEmail = emailAuthRequestDto.getEmail();
		userRepository.getUser(requestEmail, UserStatus.JOIN).ifPresent(user -> {
			throw new CustomException(ErrorCode.DUPLICATE_USER);
		});

		cleanAllEmailAuthenticationByEmail(requestEmail);

		String code = RandomStringUtils.randomNumeric(6);

		emailAuthenticationRepository.save(EmailAuthentication.create(requestEmail, code, LocalDateTime.now().plusMinutes(10)));
		emailService.sendEmailAuthenticationCodeForSignup(requestEmail, code);
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
		delete(user, sites);

		eventPublisher.publishEvent(new UserResignEvent(user.getEmail()));
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
}

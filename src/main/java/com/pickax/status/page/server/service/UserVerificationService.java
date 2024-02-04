package com.pickax.status.page.server.service;

import com.pickax.status.page.server.domain.enumclass.UserStatus;
import com.pickax.status.page.server.repository.UserRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserVerificationService {

	private final UserRepository userRepository;

	public boolean verifyValidUser(Long userId) {
		return userRepository.getUserById(userId, UserStatus.JOIN).isPresent();
	}
}

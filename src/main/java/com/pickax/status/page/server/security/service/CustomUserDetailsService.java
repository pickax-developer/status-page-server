package com.pickax.status.page.server.security.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pickax.status.page.server.domain.model.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	@Override
	@Transactional
	public CustomUserDetails loadUserByUsername(String email) {

		// TODO
		// User user = userRepository.getUserBySignIn(email)
		// 	.orElseThrow(
		// 		() -> new UsernameNotFoundException(localeMessage.getMessage(ErrorCode.INVALID_USER.toString())));
		//
		return CustomUserDetails.of(new User());
	}
}

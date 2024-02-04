package com.pickax.status.page.server.util;

import static com.pickax.status.page.server.common.exception.ErrorCode.*;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.pickax.status.page.server.common.exception.CustomException;
import com.pickax.status.page.server.security.service.CustomUserDetails;

public class SecurityUtil {
	public static Long getCurrentUserId() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || authentication.getName() == null) {
			throw new CustomException(INVALID_AUTHENTICATION);
		}

		Long userId = 0L;

		if (authentication.getPrincipal() instanceof CustomUserDetails) {
			userId = Long.parseLong(authentication.getName());
		}

		return userId;
	}
}

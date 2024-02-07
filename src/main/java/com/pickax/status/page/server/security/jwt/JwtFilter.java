package com.pickax.status.page.server.security.jwt;

import java.io.IOException;

import com.pickax.status.page.server.common.exception.CustomException;
import com.pickax.status.page.server.common.exception.ErrorCode;
import com.pickax.status.page.server.service.UserVerificationService;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
	private static final int BEARER_PREFIX_LENGTH = 7;

	private final TokenProvider tokenProvider;
	private final UserVerificationService userVerificationService;

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
		throws ServletException, IOException {
		String jwt = getJwtFromRequest(request);

		log.info("doFilterInternal 요청 : {}", jwt);

		if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
			verifyValidUser(jwt);
			Authentication authentication = tokenProvider.getAuthentication(jwt);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} else {
			log.info("해당 요청 URI: {} 에 JWT 토큰이 없습니다.", request.getRequestURI());
		}

		chain.doFilter(request, response);
	}

	private void verifyValidUser(String jwt) {
		Claims claims = tokenProvider.parseClaims(jwt);
		if (!userVerificationService.verifyValidUser(Long.parseLong(claims.getSubject()))) {
			throw new CustomException(ErrorCode.INVALID_AUTHENTICATION);
		}
	}

	private String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(BEARER_PREFIX_LENGTH);
		}
		return null;
	}
}

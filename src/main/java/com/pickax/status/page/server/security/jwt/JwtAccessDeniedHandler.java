package com.pickax.status.page.server.security.jwt;

import static com.pickax.status.page.server.common.exception.ErrorCode.*;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pickax.status.page.server.common.message.LocaleMessageConverter;
import com.pickax.status.page.server.dto.reseponse.common.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
	private final ObjectMapper objectMapper;
	private final LocaleMessageConverter localeMessage;

	@Override
	public void handle(
		HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException
	) throws IOException {
		response.setContentType("application/json;charset=UTF-8");
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		response.getWriter()
			.println(objectMapper.writeValueAsString(
				ErrorResponse.parseError(
					INVALID_AUTHORIZATION, localeMessage.getMessage(INVALID_AUTHORIZATION.toString())))
			);
	}
}

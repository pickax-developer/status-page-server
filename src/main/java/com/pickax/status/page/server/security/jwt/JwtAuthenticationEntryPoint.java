package com.pickax.status.page.server.security.jwt;

import static com.pickax.status.page.server.common.exception.ErrorCode.*;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pickax.status.page.server.common.message.LocaleMessageConverter;
import com.pickax.status.page.server.dto.reseponse.common.ErrorResponse;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
	private final ObjectMapper objectMapper;
	private final LocaleMessageConverter localeMessage;

	@Override
	public void commence(
		HttpServletRequest request, HttpServletResponse response, AuthenticationException authException
	) throws IOException, ServletException {

		response.setContentType("application/json;charset=UTF-8");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.getWriter()
			.println(objectMapper.writeValueAsString(
				ErrorResponse.parseError(
					INVALID_AUTHENTICATION, localeMessage.getMessage(INVALID_AUTHENTICATION.toString())))
			);
	}
}

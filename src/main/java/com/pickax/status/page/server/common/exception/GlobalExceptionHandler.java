package com.pickax.status.page.server.common.exception;

import com.pickax.status.page.server.common.message.LocaleMessageConverter;
import com.pickax.status.page.server.dto.reseponse.common.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

	private final LocaleMessageConverter localeMessage;

	@ExceptionHandler(value = {CustomException.class})
	protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e, HttpServletRequest request) {
		return ErrorResponse.toResponseEntity(e.getErrorCode(), localeMessage.getMessage(e.getErrorCode().toString()), request);
	}

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<ErrorResponse> handleException(Exception e, HttpServletRequest request) {
		return ErrorResponse.toResponseEntity(ErrorCode.INTERNAL_SERVER_ERROR, e.getMessage(), request);
	}
}

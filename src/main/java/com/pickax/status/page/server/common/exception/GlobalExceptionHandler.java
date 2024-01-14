package com.pickax.status.page.server.common.exception;

import com.pickax.status.page.server.common.message.LocaleMessageConverter;
import com.pickax.status.page.server.dto.reseponse.common.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

	@ExceptionHandler(ConstraintViolationException.class)
	protected ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException e, HttpServletRequest request) {
		return ErrorResponse.toResponseEntity(ErrorCode.INVALID_INPUT_VALUE, localeMessage.getMessage(ErrorCode.INVALID_INPUT_VALUE.toString()), request);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
		return ErrorResponse.toResponseEntity(ErrorCode.INVALID_INPUT_VALUE, e.getBindingResult(), request);
	}


	@ExceptionHandler(Exception.class)
	protected ResponseEntity<ErrorResponse> handleException(Exception e, HttpServletRequest request) {
		return ErrorResponse.toResponseEntity(ErrorCode.INTERNAL_SERVER_ERROR, e.getMessage(), request);
	}
}

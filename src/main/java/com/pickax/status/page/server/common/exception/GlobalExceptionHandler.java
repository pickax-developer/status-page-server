package com.pickax.status.page.server.common.exception;

import com.pickax.status.page.server.common.message.LocaleMessageConverter;
import com.pickax.status.page.server.dto.reseponse.common.ErrorResponse;
import com.pickax.status.page.server.service.slack.SlackService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

	private final LocaleMessageConverter localeMessage;
	private final SlackService slackService;

	@ExceptionHandler(value = {CustomException.class})
	protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e, HttpServletRequest request) {
		sendErrorMessageToSlack(e, localeMessage.getMessage(e.getErrorCode().toString()), e.getErrorCode().getHttpStatus());
		return ErrorResponse.toResponseEntity(e.getErrorCode(), localeMessage.getMessage(e.getErrorCode().toString()), request);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	protected ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException e, HttpServletRequest request) {
		sendErrorMessageToSlack(e, localeMessage.getMessage(ErrorCode.INVALID_INPUT_VALUE.toString()), HttpStatus.BAD_REQUEST);
		return ErrorResponse.toResponseEntity(ErrorCode.INVALID_INPUT_VALUE, localeMessage.getMessage(ErrorCode.INVALID_INPUT_VALUE.toString()), request);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
		List<FieldError> fieldErrors =  e.getBindingResult().getFieldErrors();
		String message = fieldErrors.get(0) == null ? "" : fieldErrors.get(0).getDefaultMessage();
		sendErrorMessageToSlack(e, message, HttpStatus.BAD_REQUEST);
		return ErrorResponse.toResponseEntity(ErrorCode.INVALID_INPUT_VALUE, message, fieldErrors, request);
	}

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<ErrorResponse> handleException(Exception e, HttpServletRequest request) {
		sendErrorMessageToSlack(e, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		return ErrorResponse.toResponseEntity(ErrorCode.INTERNAL_SERVER_ERROR, e.getMessage(), request);
	}

	private void sendErrorMessageToSlack(Exception e, String message, HttpStatus httpStatus) {
		slackService.sendErrorMessage(e, message, httpStatus);
	}
}

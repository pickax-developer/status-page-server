package com.pickax.status.page.server.service.slack;

import org.springframework.http.HttpStatus;

public interface ErrorAlertService {
	void sendErrorMessage(Exception exception, String message, HttpStatus httpStatus);
}

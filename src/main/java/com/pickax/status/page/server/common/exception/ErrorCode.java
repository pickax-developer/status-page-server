package com.pickax.status.page.server.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
	/* 400 BAD_REQUEST */

	/* 401 UNAUTHORIZED */

	/* 403 FORBIDDEN */

	/* 404 NOT_FOUND */

	/* 409 CONFLICT */

	/* 500 INTERNAL_SERVER_ERROR */
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR);

	private final HttpStatus httpStatus;

	ErrorCode(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}
}

package com.pickax.status.page.server.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
	/* 400 BAD_REQUEST */
	INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST),
	INVALID_REQUEST_OWNER(HttpStatus.BAD_REQUEST),
	INVALID_UNVERIFIED_SITE(HttpStatus.BAD_REQUEST),
	INVALID_META_TAG(HttpStatus.BAD_REQUEST),
	INVALID_CHECKED_META_TAG(HttpStatus.BAD_REQUEST),
	INVALID_EXPIRED_META_TAG(HttpStatus.BAD_REQUEST),
	INVALID_COMPLETED_SITE(HttpStatus.BAD_REQUEST),
	INVALID_SITE_URL(HttpStatus.BAD_REQUEST),

	/* 401 UNAUTHORIZED */

	/* 403 FORBIDDEN */

	/* 404 NOT_FOUND */
	NOT_FOUND_SITE(HttpStatus.NOT_FOUND),
	NOT_FOUND_META_TAG(HttpStatus.NOT_FOUND),

	/* 409 CONFLICT */

	/* 500 INTERNAL_SERVER_ERROR */
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR);

	private final HttpStatus httpStatus;

	ErrorCode(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}
}

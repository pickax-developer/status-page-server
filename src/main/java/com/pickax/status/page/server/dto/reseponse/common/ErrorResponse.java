package com.pickax.status.page.server.dto.reseponse.common;

import com.pickax.status.page.server.common.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@NoArgsConstructor
public class ErrorResponse {
    private int status;
    private String error;
    private String customError;
    private String message;
    private String path;
    private final String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    private List<FieldError> errors;

    @Builder
    private ErrorResponse(
            int status, String error, String customError, String message, String path, List<FieldError> errors
    ) {
        this.status = status;
        this.error = error;
        this.customError = customError;
        this.message = message;
        this.path = path;
        this.error = error;
    }

    public static ResponseEntity<ErrorResponse> toResponseEntity(
            ErrorCode errorCode, String message, HttpServletRequest request
    ) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ErrorResponse.builder()
                        .status(errorCode.getHttpStatus().value())
                        .error(errorCode.getHttpStatus().name())
                        .customError(errorCode.name())
                        .message(message)
                        .path(request.getRequestURI())
                        .build()
                );
    }

    public static ResponseEntity<ErrorResponse> toResponseEntity(
            ErrorCode errorCode, final String message, List<FieldError> fieldErrors, HttpServletRequest request
    ) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ErrorResponse.builder()
                        .status(errorCode.getHttpStatus().value())
                        .error(errorCode.getHttpStatus().name())
                        .customError(errorCode.name())
                        .message(message)
                        .errors(fieldErrors)
                        .path(request.getRequestURI())
                        .build()
                );
    }

    public static ErrorResponse parseError(ErrorCode errorCode, String message) {
        return ErrorResponse.builder()
            .status(errorCode.getHttpStatus().value())
            .error(errorCode.getHttpStatus().name())
            .customError(errorCode.name())
            .message(message)
            .build();
    }
}

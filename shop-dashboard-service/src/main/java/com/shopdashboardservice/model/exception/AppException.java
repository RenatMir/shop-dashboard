package com.shopdashboardservice.model.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class AppException extends RuntimeException{

    private final HttpStatus httpStatus;

    private final ErrorCode errorCode;

    private final String message;

    public AppException(final ErrorCode errorCode){
        this(map(errorCode), errorCode);
    }

    public AppException(final HttpStatus httpStatus, final ErrorCode errorCode){
        this(httpStatus, errorCode, null);
    }

    public AppException(final ErrorCode errorCode, final String message, Object... args) {
        this(map(errorCode), errorCode, String.format(message, args));
    }
    private static HttpStatus map(final ErrorCode errorCode) {
        switch (errorCode) {
            case BAD_REQUEST:
                return HttpStatus.BAD_REQUEST;
            default:
                return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    public enum ErrorCode {
        TECHNICAL_ERROR,
        BAD_REQUEST,
        OPTIMISTIC_LOCK_FAILED;
    }
}

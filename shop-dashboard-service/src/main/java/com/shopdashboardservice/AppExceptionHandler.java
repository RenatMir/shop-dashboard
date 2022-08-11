package com.shopdashboardservice;

import com.shopdashboardservice.model.ErrorResponse;
import com.shopdashboardservice.model.exception.AppException;
import com.shopdashboardservice.model.exception.AppException.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorResponse> handleAppException(final AppException exception) {

        final ErrorResponse response = new ErrorResponse()
                .setErrorCode(exception.getErrorCode())
                .setMessage(exception.getMessage());

        return ResponseEntity
                .status(exception.getHttpStatus())
                .body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(final Exception exception) {

        final ErrorResponse response = new ErrorResponse()
                .setErrorCode(ErrorCode.TECHNICAL_ERROR)
                .setMessage(exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}

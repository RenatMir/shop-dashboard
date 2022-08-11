package com.shopdashboardservice.model;

import com.shopdashboardservice.model.exception.AppException.ErrorCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ErrorResponse {

    private ErrorCode errorCode;

    private String message;

    private String details;
}

package com.ldz.SpendWise.exception;

import com.ldz.SpendWise.exception.handler.ErrorCode;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class ValidationException extends ApplicationException {
    private static final String APP_ERROR_CODE = "VALIDATION_ERROR";
    private final List<String> validationErrors;

    public ValidationException(String validationError) {
        super(validationError, ErrorCode.BAD_REQUEST, APP_ERROR_CODE, new HashMap<>());
        validationErrors = List.of(validationError);
    }

    public ValidationException(String errorMessage, ErrorCode errorCode, Map<String, Object> appParams, List<String> validationErrors) {
        super(errorMessage, errorCode, APP_ERROR_CODE, appParams);
        this.validationErrors = validationErrors;
    }

}

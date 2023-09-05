package com.ldz.SpendWise.exception;

import com.ldz.SpendWise.exception.handler.ErrorCode;

import java.util.HashMap;
import java.util.Map;

public class UnknownException extends ApplicationException {

    private static final String APP_ERROR_CODE = "UNKNOWN_EXCEPTION";

    public UnknownException(Exception exception) {
        super(exception.getMessage(), ErrorCode.BAD_REQUEST, APP_ERROR_CODE, Map.of("exception", exception.toString()));
    }

    public UnknownException(String message) {
        super(message, ErrorCode.BAD_REQUEST, APP_ERROR_CODE, new HashMap<>());
    }

}

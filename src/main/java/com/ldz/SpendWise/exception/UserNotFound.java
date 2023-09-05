package com.ldz.SpendWise.exception;

import com.ldz.SpendWise.exception.handler.ErrorCode;

import java.util.Map;

public class UserNotFound extends ApplicationException {

    private static final String APP_ERROR_CODE = "USER_NOT_FOUND";

    public UserNotFound(Long userId) {
        super(String.format("User id=[%s] not found", userId), ErrorCode.NOT_FOUND, APP_ERROR_CODE, Map.of("userId", userId));
    }

    public UserNotFound(String username) {
        super(String.format("User [%s] not found", username), ErrorCode.NOT_FOUND, APP_ERROR_CODE, Map.of("username", username));
    }

}

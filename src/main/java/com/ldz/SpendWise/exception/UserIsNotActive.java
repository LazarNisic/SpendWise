package com.ldz.SpendWise.exception;

import com.ldz.SpendWise.exception.handler.ErrorCode;

import java.util.Map;

public class UserIsNotActive extends ApplicationException {

    private static final String APP_ERROR_CODE = "USER_IS_NOT_ACTIVE";

    public UserIsNotActive(Long userId) {
        super(String.format("User id=[%s] is not active", userId), ErrorCode.BAD_REQUEST, APP_ERROR_CODE, Map.of("userId", userId));
    }

    public UserIsNotActive(String username) {
        super(String.format("User [%s] is not active", username), ErrorCode.BAD_REQUEST, APP_ERROR_CODE, Map.of("username", username));
    }

}

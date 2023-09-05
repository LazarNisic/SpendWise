package com.ldz.SpendWise.exception;

import com.ldz.SpendWise.exception.handler.ErrorCode;

public class ChangeUsernameException extends ApplicationException {

    private static final String APP_ERROR_CODE = "CAN_NOT_CHANGE_USERNAME";

    private static final String MESSAGE = "Username does not match. Username can not be changed";

    public ChangeUsernameException() {
        super(MESSAGE, ErrorCode.BAD_REQUEST, APP_ERROR_CODE);
    }
}

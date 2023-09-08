package com.ldz.SpendWise.exception;

import com.ldz.SpendWise.exception.handler.ErrorCode;

import java.util.Map;

public class BalanceNotFound extends ApplicationException {

    private static final String APP_ERROR_CODE = "BALANCE_NOT_FOUND";

    public BalanceNotFound(Long balanceId) {
        super(String.format("Balance id=[%s] not found", balanceId), ErrorCode.NOT_FOUND, APP_ERROR_CODE, Map.of("balanceId", balanceId));
    }

}

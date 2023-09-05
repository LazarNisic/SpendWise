package com.ldz.SpendWise.exception;

import com.ldz.SpendWise.exception.handler.ErrorCode;

import java.util.Map;

public class ExtraIncomeNotFound extends ApplicationException {

    private static final String APP_ERROR_CODE = "EXTRA_INCOME_NOT_FOUND";

    public ExtraIncomeNotFound(Long extraIncomeId) {
        super(String.format("Extra income id=[%s] not found", extraIncomeId), ErrorCode.NOT_FOUND, APP_ERROR_CODE, Map.of("extraIncomeId", extraIncomeId));
    }

}
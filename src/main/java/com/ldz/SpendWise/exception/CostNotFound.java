package com.ldz.SpendWise.exception;

import com.ldz.SpendWise.exception.handler.ErrorCode;

import java.util.Map;

public class CostNotFound extends ApplicationException {

    private static final String APP_ERROR_CODE = "COST_NOT_FOUND";

    public CostNotFound(Long costId) {
        super(String.format("Cost id=[%s] not found", costId), ErrorCode.NOT_FOUND, APP_ERROR_CODE, Map.of("costId", costId));
    }
}

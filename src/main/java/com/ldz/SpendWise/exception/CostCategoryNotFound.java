package com.ldz.SpendWise.exception;

import com.ldz.SpendWise.exception.handler.ErrorCode;

import java.util.Map;

public class CostCategoryNotFound extends ApplicationException {

    private static final String APP_ERROR_CODE = "COST_CATEGORY_NOT_FOUND";

    public CostCategoryNotFound(Long costCategoryId) {
        super(String.format("Cost category id=[%s] not found", costCategoryId), ErrorCode.NOT_FOUND, APP_ERROR_CODE, Map.of("costCategoryId", costCategoryId));
    }

    public CostCategoryNotFound(String name) {
        super(String.format("Cost category [%s] not found", name), ErrorCode.NOT_FOUND, APP_ERROR_CODE, Map.of("name", name));
    }

}

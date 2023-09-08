package com.ldz.SpendWise.exception;

import com.ldz.SpendWise.exception.handler.ErrorCode;

import java.util.Map;

public class MonthlySalaryNotFound extends ApplicationException {

    private static final String APP_ERROR_CODE = "MONTHLY_SALARY_NOT_FOUND";

    public MonthlySalaryNotFound(Long monthlySalaryId) {
        super(String.format("Monthly salary id=[%s] not found", monthlySalaryId), ErrorCode.NOT_FOUND, APP_ERROR_CODE, Map.of("monthlySalaryId", monthlySalaryId));
    }

    public MonthlySalaryNotFound(String message) {
        super(String.format("[%s]", message), ErrorCode.NOT_FOUND, APP_ERROR_CODE);
    }

}

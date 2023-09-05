package com.ldz.SpendWise.exception.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class ErrorResponse {
    private final LocalDateTime timestamp;
    private final String errorCode;
    private final String appCode;
    private final Map<String, Object> appParams;
    private final List<String> errorMessages;

    public ErrorResponse(String errorCode, String appCode, Map<String, Object> appParams, List<String> errorMessages) {
        this.timestamp = LocalDateTime.now();
        this.errorCode = errorCode;
        this.appCode = appCode;
        this.appParams = appParams;
        this.errorMessages = errorMessages;
    }
}

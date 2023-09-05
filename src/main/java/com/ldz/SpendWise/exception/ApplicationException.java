package com.ldz.SpendWise.exception;

import com.ldz.SpendWise.exception.handler.ErrorCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Getter
public class ApplicationException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String appCode;
    private final Map<String, Object> appParams;

    public ApplicationException(String message, ErrorCode errorCode, String appCode) {
        super(message);
        log.error(message);
        this.errorCode = errorCode;
        this.appCode = appCode;
        this.appParams = new HashMap<>();
    }

    public ApplicationException(String message, ErrorCode errorCode, String appCode, Map<String, Object> appParams) {
        super(message);
        log.error(message);
        this.errorCode = errorCode;
        this.appCode = appCode;
        this.appParams = appParams;
    }

    public ApplicationException(String message, ErrorCode errorCode) {
        super(message);
        log.error(message);
        this.errorCode = errorCode;
        this.appCode = null;
        this.appParams = new HashMap<>();
    }

    public ApplicationException(String message) {
        super(message);
        log.error(message);
        this.errorCode = ErrorCode.BAD_REQUEST;
        this.appCode = null;
        this.appParams = new HashMap<>();
    }

    public ApplicationException(Throwable throwable) {
        super(throwable.getMessage());
        log.error(throwable.getMessage());
        this.errorCode = ErrorCode.BAD_REQUEST;
        this.appCode = null;
        this.appParams = new HashMap<>();
    }
}

package com.ldz.SpendWise.exception.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    BAD_REQUEST("BAD_REQUEST", 400),
    UNAUTHORIZED("UNAUTHORIZED", 401),
    FORBIDDEN("FORBIDDEN", 403),
    NOT_FOUND("NOT_FOUND", 404),
    CONFLICT("CONFLICT", 409),
    // add custom error here 460-490
    OTHER_ERROR("OTHER_ERROR", 460),
    RUNTIME_ERROR("RUNTIME_ERROR", 490);

    private final String code;
    private final int httpStatus;
}

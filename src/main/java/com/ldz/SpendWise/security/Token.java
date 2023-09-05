package com.ldz.SpendWise.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class Token implements Serializable {
    private String jwt;
    private TokenHeader tokenHeader;
    private TokenPayload tokenPayload;
    private Map<String, Object> claims;
}

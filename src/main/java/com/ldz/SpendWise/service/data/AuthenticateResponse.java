package com.ldz.SpendWise.service.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ldz.SpendWise.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticateResponse {
    @JsonIgnore
    private Exception exception;
    private String accessToken;
    private String refreshToken;
    private LocalDateTime accessTokenValidUntil;
    private LocalDateTime refreshTokenValidUntil;
    private UserDTO user;

    public AuthenticateResponse(Exception exception) {
        this.exception = exception;
    }
}

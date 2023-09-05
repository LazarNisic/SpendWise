package com.ldz.SpendWise.service.data;

import com.ldz.SpendWise.enums.TokenGrantType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticateRequest {
    @NotBlank(message = "Password cannot be empty")
    private String password;
    @NotBlank(message = "Username cannot be empty")
    private String username;
    @NotBlank(message = "Authentication grant type")
    private TokenGrantType grantType;
    private String refreshToken;
}

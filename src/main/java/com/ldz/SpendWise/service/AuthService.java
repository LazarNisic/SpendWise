package com.ldz.SpendWise.service;

import com.ldz.SpendWise.service.data.AuthenticateRequest;
import com.ldz.SpendWise.service.data.AuthenticateResponse;

public interface AuthService {
    AuthenticateResponse authenticate(AuthenticateRequest authenticateRequest);
}

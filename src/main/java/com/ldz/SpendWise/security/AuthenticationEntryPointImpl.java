package com.ldz.SpendWise.security;

import com.ldz.SpendWise.exception.handler.ErrorResponse;
import com.ldz.SpendWise.util.SerializerUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    private final SerializerUtil serializerUtil;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ErrorResponse errorResponse = new ErrorResponse("UNAUTHORIZED", "UNAUTHORIZED",
                Map.of("path", request.getServletPath()), List.of(authException.getMessage()));
        response.getWriter().write(serializerUtil.toJson(errorResponse));
        response.getWriter().flush();
    }
}

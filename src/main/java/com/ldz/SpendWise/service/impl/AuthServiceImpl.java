package com.ldz.SpendWise.service.impl;

import com.ldz.SpendWise.configuration.AppConfiguration;
import com.ldz.SpendWise.dto.UserDTO;
import com.ldz.SpendWise.enums.TokenGrantType;
import com.ldz.SpendWise.exception.BadCredentials;
import com.ldz.SpendWise.exception.UserNotFound;
import com.ldz.SpendWise.mapper.UserMapper;
import com.ldz.SpendWise.model.User;
import com.ldz.SpendWise.repository.UserRepository;
import com.ldz.SpendWise.security.JwtUtil;
import com.ldz.SpendWise.service.AuthService;
import com.ldz.SpendWise.service.data.AuthenticateRequest;
import com.ldz.SpendWise.service.data.AuthenticateResponse;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final AppConfiguration appConfiguration;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public AuthenticateResponse authenticate(AuthenticateRequest authenticateRequest) {
        User user;
        String jwtSecret = appConfiguration.getJwt().getSecret();
        if (authenticateRequest.getGrantType().equals(TokenGrantType.ACCESS)) {
            user = userRepository.findByUsername(authenticateRequest.getUsername()).orElseThrow(() -> new UserNotFound(authenticateRequest.getUsername()));
        } else {
            final Claims claims = jwtUtil.authenticateToken(authenticateRequest.getRefreshToken(), TokenGrantType.REFRESH, jwtSecret);
            Long userId = claims.get("userId", Long.class);
            user = userRepository.findById(userId).orElseThrow(() -> new UserNotFound(userId));
        }
        UserDTO userDTO = userMapper.toDto(user);

        long accessExpirationMs = appConfiguration.getJwt().getAccessExpirationMs();
        long refreshExpirationMs = appConfiguration.getJwt().getRefreshExpirationMs();
        if (authenticateRequest.getGrantType().equals(TokenGrantType.ACCESS)) {
            try {
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(user.getUsername(), authenticateRequest.getPassword()));
            } catch (BadCredentialsException e) {
                return new AuthenticateResponse(new BadCredentials());
            } catch (Exception e) {
                return new AuthenticateResponse(e);
            }
        }
        final String accessJwt = jwtUtil.generateAccessToken(userDTO, accessExpirationMs, jwtSecret);
        final String refreshJwt = jwtUtil.generateRefreshToken(userDTO, refreshExpirationMs, jwtSecret);
        LocalDateTime accessTokenValidUntil = jwtUtil.extractExpiration(accessJwt, jwtSecret);
        LocalDateTime refreshTokenValidUntil = jwtUtil.extractExpiration(refreshJwt, jwtSecret);
        return new AuthenticateResponse(null, accessJwt, refreshJwt, accessTokenValidUntil, refreshTokenValidUntil, userDTO);
    }
}

package com.ldz.SpendWise.security;

import com.ldz.SpendWise.configuration.AppConfiguration;
import com.ldz.SpendWise.enums.TokenType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

@RequiredArgsConstructor
@Slf4j
public class AppUserDetails implements UserDetails {
    private final Token token;
    private final JwtUtil jwtUtil;
    private final AppConfiguration appConfiguration;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (token.getTokenPayload().getTokenType().equals(TokenType.USER) && token.getClaims().containsKey("authorities")) {
            return Arrays.stream(token.getClaims().get("authorities").toString()
                            .split(","))
                    .map(s -> (GrantedAuthority) () -> s).toList();
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return switch (token.getTokenPayload().getTokenType()) {
            case USER -> jwtUtil.extractUserName(token.getJwt(), appConfiguration.getJwt().getSecret());
            case APPLICATION -> token.getTokenPayload().getApiKey();
        };
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

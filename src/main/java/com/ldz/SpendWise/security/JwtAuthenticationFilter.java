package com.ldz.SpendWise.security;

import com.ldz.SpendWise.configuration.AppConfiguration;
import com.ldz.SpendWise.enums.TokenGrantType;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final AppConfiguration appConfiguration;
    private static final String TOKEN_GRANT_TYPE = "tokenGrantType";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwtSecret = appConfiguration.getJwt().getSecret();
        try {
            String jwt = parseJwt(request);
            Token token = jwtUtil.getToken(jwt);
            if (jwt != null && token != null) {
                jwtUtil.setTokenClaims(token, jwtSecret);
                if (token.getClaims() != null && token.getClaims().containsKey(TOKEN_GRANT_TYPE) && token.getClaims().get(TOKEN_GRANT_TYPE).equals(TokenGrantType.ACCESS.name())) {
                    UserDetails userDetails = new AppUserDetails(token, jwtUtil, appConfiguration);
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContext context = SecurityContextHolder.createEmptyContext();
                    context.setAuthentication(authentication);
                    SecurityContextHolder.setContext(context);
                }
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }
        filterChain.doFilter(request, response);
    }

    public static String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && !headerAuth.startsWith("Bearer ")) {
            return headerAuth;
        } else if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }

        return null;
    }
}

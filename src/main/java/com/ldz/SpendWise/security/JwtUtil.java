package com.ldz.SpendWise.security;

import com.ldz.SpendWise.dto.UserDTO;
import com.ldz.SpendWise.enums.Role;
import com.ldz.SpendWise.exception.SessionExpired;
import com.ldz.SpendWise.util.SerializerUtil;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import com.ldz.SpendWise.enums.TokenGrantType;
import com.ldz.SpendWise.enums.TokenType;
import com.ldz.SpendWise.exception.ApplicationException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.function.Function;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtUtil {
    private final SerializerUtil serializerUtil;
    private static final String AUTHORITIES = "authorities";
    private static final String USER_ID = "userId";
    private static final String TOKEN_TYPE = "tokenType";
    private static final String TOKEN_GRANT_TYPE = "tokenGrantType";


    public String extractUserName(String token, String jwtToken) {
        return extractClaim(token, jwtToken, Claims::getSubject);
    }

    public LocalDateTime extractExpiration(String token, String jwtSecret) {
        return LocalDateTime.ofInstant(extractClaim(token, jwtSecret, Claims::getExpiration).toInstant(), ZoneId.systemDefault());
    }

    private <T> T extractClaim(String token, String jwtSecret, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token, jwtSecret);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token, String jwtSecret) {
        return Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJws(token).getBody();
    }

    public String generateAccessToken(UserDTO userDTO, long accessExpirationMs, String jwtSecret) {
        Map<String, Object> claims = new HashMap<>();
        List<String> authorities = userDTO.getRoles().stream().map(Role::getCode).toList();
        claims.put(AUTHORITIES, StringUtils.join(authorities, ","));
        claims.put(USER_ID, userDTO.getId());
        claims.put(TOKEN_TYPE, TokenType.USER);
        claims.put(TOKEN_GRANT_TYPE, TokenGrantType.ACCESS);
        return createToken(claims, userDTO.getUsername(), new Date(System.currentTimeMillis() + accessExpirationMs), jwtSecret);
    }

    public String generateRefreshToken(UserDTO userDTO, long refreshExpirationMs, String jwtSecret) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(USER_ID, userDTO.getId());
        claims.put(TOKEN_TYPE, TokenType.USER);
        claims.put(TOKEN_GRANT_TYPE, TokenGrantType.REFRESH);
        return createToken(claims, userDTO.getUsername(), new Date(System.currentTimeMillis() + refreshExpirationMs), jwtSecret);
    }

    private String createToken(Map<String, Object> claims, String subject, Date expirationDate, String jwtSecret) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    public void setTokenClaims(Token token, String jwtSecret) {
        try {
            if (token.getTokenPayload().getTokenType().equals(TokenType.USER)) {
                Jwts.parserBuilder()
                        .setSigningKey(jwtSecret)
                        .build()
                        .parseClaimsJws(token.getJwt())
                        .getBody()
                        .forEach((key, value) -> token.getClaims().put(key, value));
            } else if (token.getTokenPayload().getTokenType().equals(TokenType.APPLICATION)) {
                throw new ApplicationException("APPLICATION Token is not implemented");
            } else {
                log.error("Unknown token type");
            }
        } catch (SecurityException e) {
            log.error(e.toString());
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature.");
            log.trace("Invalid JWT signature trace: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
            log.trace("Expired JWT token trace: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
            log.trace("Unsupported JWT token trace: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
            log.trace("JWT token compact of handler are invalid trace: {}", e.getMessage());
        }
    }

    public Token getToken(String jwt) {
        if (StringUtils.isEmpty(jwt)) {
            return null;
        }

        try {
            String[] chunks = jwt.split("\\.");
            Base64.Decoder decoder = Base64.getUrlDecoder();

            String header = new String(decoder.decode(chunks[0]));
            String payload = new String(decoder.decode(chunks[1]));
            TokenHeader tokenHeader = serializerUtil.fromJson(header, TokenHeader.class);
            TokenPayload tokenPayload = serializerUtil.fromJson(payload, TokenPayload.class);

            return new Token(jwt, tokenHeader, tokenPayload, new HashMap<>());
        } catch (Exception e) {
            log.error(e.toString());
            return null;
        }
    }

    public Claims authenticateToken(String token, TokenGrantType grantType, String jwtSecret) {
        try {
            log.info("Authenticate token of type: {}", grantType);
            Claims claims = Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJws(token).getBody();
            String tokenTypeString = claims.get(TOKEN_TYPE, String.class);
            String tokenGrantTypeString = claims.get(TOKEN_GRANT_TYPE, String.class);

            if (StringUtils.isBlank(tokenTypeString) || StringUtils.isBlank(tokenGrantTypeString)) {
                log.info("Token authentication failed, token type/category is missing");
                throw new BadCredentialsException("Invalid token type");
            }

            TokenGrantType tokenGrantType = TokenGrantType.valueOf(tokenGrantTypeString);
            if (tokenGrantType != grantType) {
                log.info("Token authentication failed, invalid category, token category: {}, requested token grant type: {}", tokenGrantType, grantType);
                throw new BadCredentialsException("Invalid token type");
            }
            return claims;
        } catch (SecurityException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            log.info("Token authentication failed for credentials: {} message: {}", token, e.getMessage());
            throw new BadCredentialsException("Invalid token");
        } catch (ExpiredJwtException e) {
            log.info("Token expired for credentials: {} message: {}", token, e.getMessage());
            throw new SessionExpired();
        }
    }
}

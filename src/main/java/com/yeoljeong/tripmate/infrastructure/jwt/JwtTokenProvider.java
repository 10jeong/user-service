package com.yeoljeong.tripmate.infrastructure.jwt;

import com.yeoljeong.tripmate.application.port.JwtPort;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtTokenProvider implements JwtPort {

    private final JwtProperties jwtProperties;
    private SecretKey signingKey;

    @PostConstruct
    public void init() {
        this.signingKey = Keys.hmacShaKeyFor(
            jwtProperties.secret().getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(UUID userId, String role) {
        Date now = new Date();
        String jti = UUID.randomUUID().toString();
        Date expirationDate = new Date(now.getTime() + jwtProperties.accessTokenExpiration());

        return Jwts.builder()
            .subject(String.valueOf(userId))
            .issuer("tripMate")
            .claim("role", role)
            .issuedAt(now)
            .expiration(expirationDate)
            .id(jti)
            .signWith(signingKey)
            .compact();
    }

    public String generateRefreshToken(UUID userId) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + jwtProperties.refreshTokenExpiration());

        return Jwts.builder()
            .subject(String.valueOf(userId))
            .issuer("tripMate")
            .issuedAt(now)
            .expiration(expirationDate)
            .signWith(signingKey)
            .compact();
    }

    public long getRefreshExpiration() {
        return jwtProperties.refreshTokenExpiration();
    }

    @Override
    public long getAccessTokenExpiration() {
        return jwtProperties.accessTokenExpiration();
    }

    /* 서명을 검증합니다. TODO Gateway에 위치한 코드와 중복되어 공통화 작업이 필요 */
    @Override
    public boolean validateToken(String token) {
        try {
            Claims claims = getClaims(token);

            String subject = claims.getSubject();

            return subject != null && !subject.isBlank();
        } catch (Exception e) {
            log.warn("[JwtProvider] 토큰 검증 실패: {}", e.getMessage());
            return false;
        }
    }

    /* subject를 추출합니다.*/
    @Override
    public String getUserId(String token) {
        Claims claims = getClaims(token);
        return claims.getSubject();
    }

    // helper method
    private Claims getClaims(String token) {
        return Jwts.parser()
            .verifyWith(signingKey)
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }
}
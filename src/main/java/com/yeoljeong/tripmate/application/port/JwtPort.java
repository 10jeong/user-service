package com.yeoljeong.tripmate.application.port;

import java.util.UUID;

public interface JwtPort {
    String generateAccessToken(UUID userId, String role);
    String generateRefreshToken(UUID userId);
    long getRefreshExpiration();
    long getAccessTokenExpiration();
}

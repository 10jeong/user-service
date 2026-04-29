package com.yeoljeong.tripmate.application.dto.result;

public record LoginResult(String accessToken,
                          String refreshToken,
                          String tokenType,
                          long expiresIn) {

    public static LoginResult from(String accessToken, String refreshToken, String tokenType,
        long expiresIn) {
        return new LoginResult(accessToken, refreshToken, tokenType, expiresIn);
    }
}

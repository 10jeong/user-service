package com.yeoljeong.tripmate.application.dto.result;

public record LoginResult(String accessToken,
                          String refreshToken,
                          String tokenType,
                          long expiresIn) {

    public static LoginResult from(LoginResult result) {
        return new LoginResult(result.accessToken(), result.refreshToken(), result.tokenType(),
            result.expiresIn());
    }
}

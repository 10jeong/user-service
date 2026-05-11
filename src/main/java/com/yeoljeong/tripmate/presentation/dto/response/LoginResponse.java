package com.yeoljeong.tripmate.presentation.dto.response;

public record LoginResponse(String accessToken,
                            String refreshToken,
                            String tokenType,
                            long expiresIn) {

}

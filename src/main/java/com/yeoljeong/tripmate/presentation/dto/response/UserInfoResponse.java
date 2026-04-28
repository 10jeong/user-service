package com.yeoljeong.tripmate.presentation.dto.response;

import com.yeoljeong.tripmate.application.dto.result.UserCreateResult;
import java.time.LocalDateTime;
import java.util.UUID;

public record UserInfoResponse(UUID userId, String email, LocalDateTime createdAt) {

    public static UserInfoResponse from(UserCreateResult result) {
        return new UserInfoResponse(result.id(), result.email(), result.createdAt());
    }
}

package com.yeoljeong.tripmate.application.dto.result;

import com.yeoljeong.tripmate.domain.model.User;
import java.time.LocalDateTime;
import java.util.UUID;

public record UserCreateResult(UUID id, String email, LocalDateTime createdAt) {

    public static UserCreateResult from(User user) {
        return new UserCreateResult(user.getId(), user.getEmail(), user.getCreatedAt());
    }
}

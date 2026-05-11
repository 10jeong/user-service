package com.yeoljeong.tripmate.presentation.dto.response;

import com.yeoljeong.tripmate.application.dto.result.UserDetailsResult;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record UserDetailsResponse(UUID id, String email, String name, String gender,
                                  LocalDate birthDate,
                                  String role, LocalDateTime createdAt, LocalDateTime updatedAt) {

    public static UserDetailsResponse from(UserDetailsResult result) {
        return new UserDetailsResponse(result.id(), result.email(), result.name(), result.gender(),
            result.birthDate(),
            result.role(),
            result.createdAt(), result.updatedAt());
    }
}

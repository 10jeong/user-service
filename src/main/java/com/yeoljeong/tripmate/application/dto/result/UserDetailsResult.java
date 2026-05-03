package com.yeoljeong.tripmate.application.dto.result;

import com.yeoljeong.tripmate.domain.model.User;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record UserDetailsResult(UUID id, String email, String name, String gender,
                                LocalDate birthDate,
                                String role, LocalDateTime createdAt, LocalDateTime updatedAt) {

    public static UserDetailsResult from(User user) {
        return new UserDetailsResult(user.getId(), user.getEmail(), user.getName(),
            user.getGender().name(), user.getBirthDate()
            , user.getRole().name(), user.getCreatedAt(), user.getUpdatedAt());
    }
}

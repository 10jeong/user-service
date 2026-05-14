package com.yeoljeong.tripmate.application.dto.result;

import com.yeoljeong.tripmate.domain.model.User;
import java.util.List;
import java.util.UUID;

public record UserSummaryResult(UUID id, String email, String name) {

    public static List<UserSummaryResult> from(List<User> users) {
        return users.stream()
            .map(user -> new UserSummaryResult(user.getId(), user.getEmail(), user.getName()))
            .toList();
    }
}

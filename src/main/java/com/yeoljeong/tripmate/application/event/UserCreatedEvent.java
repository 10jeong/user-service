package com.yeoljeong.tripmate.application.event;

import com.yeoljeong.tripmate.domain.model.User;
import java.util.UUID;

public record UserCreatedEvent(UUID userId, String gender) {

    public static UserCreatedEvent from(User user) {
        return new UserCreatedEvent(user.getId(), user.getGender().name());
    }
}

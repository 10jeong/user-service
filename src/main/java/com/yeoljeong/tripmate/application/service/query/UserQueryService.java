package com.yeoljeong.tripmate.application.service.query;

import com.yeoljeong.tripmate.domain.model.User;
import java.util.Optional;
import java.util.UUID;

public interface UserQueryService {

    Optional<User> findByEmail(String email);

    Optional<User> findByUserId(UUID userId);
}

package com.yeoljeong.tripmate.domain.repository;

import com.yeoljeong.tripmate.domain.model.User;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    User save(User user);

    Optional<User> findByEmail(String email);

    Optional<User> findById(UUID userId);
}

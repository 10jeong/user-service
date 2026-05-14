package com.yeoljeong.tripmate.infrastructure.persistence.jpa;

import com.yeoljeong.tripmate.domain.model.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmailAndIsDeletedFalse(String email);

    Optional<User> findByIdAndIsDeletedFalse(UUID userId);

    List<User> findAllByIdIn(List<UUID> uuids);
}

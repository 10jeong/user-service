package com.yeoljeong.tripmate.infrastructure.persistence.jpa;

import com.yeoljeong.tripmate.domain.constants.OutboxStatus;
import com.yeoljeong.tripmate.infrastructure.persistence.outbox.UserOutbox;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserOutboxRepository extends JpaRepository<UserOutbox, UUID> {

    List<UserOutbox> findTop100ByStatusOrderByCreatedAtAsc(OutboxStatus status);

}

package com.yeoljeong.tripmate.infrastructure.messaging;

import com.yeoljeong.tripmate.domain.constants.OutboxStatus;
import com.yeoljeong.tripmate.infrastructure.persistence.jpa.UserOutboxRepository;
import com.yeoljeong.tripmate.infrastructure.persistence.outbox.UserOutbox;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserOutboxReader {

    private final UserOutboxRepository userOutboxRepository;

    public List<UserOutbox> findPending() {
        return userOutboxRepository.findTop100ByStatusOrderByCreatedAtAsc(OutboxStatus.PENDING);
    }

}

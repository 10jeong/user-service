package com.yeoljeong.tripmate.infrastructure.messaging;

import com.yeoljeong.tripmate.infrastructure.persistence.jpa.UserOutboxRepository;
import com.yeoljeong.tripmate.infrastructure.persistence.outbox.UserOutbox;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UserOutboxUpdater {

    private final UserOutboxRepository userOutboxRepository;

    @Transactional
    public void markPublished(UUID outboxId) {
        UserOutbox outbox = getOutboxById(outboxId);

        outbox.published();
    }

    @Transactional
    public void markFailed(UUID outboxId) {
        UserOutbox outbox = getOutboxById(outboxId);
        outbox.fail();
    }

    private UserOutbox getOutboxById(UUID outboxId) {
        return userOutboxRepository.findById(outboxId)
            .orElseThrow(() -> new RuntimeException("Outbox not found: " + outboxId));
    }
}

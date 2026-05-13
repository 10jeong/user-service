package com.yeoljeong.tripmate.infrastructure.messaging;

import com.yeoljeong.tripmate.domain.constants.OutboxStatus;
import com.yeoljeong.tripmate.infrastructure.persistence.jpa.UserOutboxRepository;
import com.yeoljeong.tripmate.infrastructure.persistence.outbox.UserOutbox;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserOutboxDispatcher {

    private final UserOutboxRepository userOutboxRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Scheduled(fixedDelay = 5000)
    @Transactional
    public void dispatch() {
        List<UserOutbox> events = userOutboxRepository
            .findTop100ByStatusOrderByCreatedAtAsc(OutboxStatus.PENDING);

        events.forEach(outbox -> {
            try {
                kafkaTemplate.send(outbox.getTopic(), outbox.getPayload()).get(); // 동기 대기
                outbox.published();
            } catch (Exception e) {
                log.error("[OUTBOX_DISPATCHER] 발행 실패 - topic: {}, id: {}", outbox.getTopic(),
                    outbox.getId(), e);
                outbox.fail();
            }
        });
    }

}

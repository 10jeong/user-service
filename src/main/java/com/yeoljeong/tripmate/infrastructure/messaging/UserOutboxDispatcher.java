package com.yeoljeong.tripmate.infrastructure.messaging;

import com.yeoljeong.tripmate.infrastructure.persistence.outbox.UserOutbox;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserOutboxDispatcher {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final UserOutboxReader userOutboxReader;
    private final UserOutboxUpdater userOutboxUpdater;

    @Scheduled(fixedDelay = 5000)
    public void dispatch() {
        List<UserOutbox> events = userOutboxReader.findPending();

        events.forEach(outbox -> {
            try {
                kafkaTemplate.send(outbox.getTopic(), outbox.getPayload()).get();
                userOutboxUpdater.markPublished(outbox.getId());
            } catch (Exception e) {
                log.error("[OUTBOX_DISPATCHER] 발행 실패 - topic: {}, id: {}", outbox.getTopic(),
                    outbox.getId(), e);
                userOutboxUpdater.markFailed(outbox.getId());
            }
        });
    }

}

package com.yeoljeong.tripmate.infrastructure.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yeoljeong.tripmate.application.port.UserEventPublisher;
import com.yeoljeong.tripmate.event.UserCreatedEvent;
import com.yeoljeong.tripmate.event.UserWithdrawalEvent;
import com.yeoljeong.tripmate.event.enums.UserTopic;
import com.yeoljeong.tripmate.infrastructure.persistence.jpa.UserOutboxRepository;
import com.yeoljeong.tripmate.infrastructure.persistence.outbox.UserOutbox;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class OutboxUserEventPublisher implements UserEventPublisher {

    private final ObjectMapper objectMapper;
    private final UserOutboxRepository userOutboxRepository;

    @Override
    public void publish(UserCreatedEvent event) {
        try {
            String payload = objectMapper.writeValueAsString(event);
            UserOutbox outbox = UserOutbox.create(UserTopic.USER_CREATED_TOPIC, payload);
            userOutboxRepository.save(outbox);
            log.info("UserCreatedEvent published: {}", payload);
        } catch (JsonProcessingException e) {
            log.error("UserCreatedEvent 직렬화 실패: {}", e.getMessage());
            throw new RuntimeException("이벤트 직렬화 실패", e);
        }
    }

    @Override
    public void publish(UserWithdrawalEvent event) {
        try {
            String payload = objectMapper.writeValueAsString(event);
            UserOutbox outbox = UserOutbox.create(UserTopic.USER_WITHDRAWAL_COMPLETED_TOPIC, payload);
            userOutboxRepository.save(outbox);
            log.info("UserWithdrawalEvent published: {}", payload);
        } catch (JsonProcessingException e) {
            log.error("UserWithdrawalEvent 직렬화 실패: {}", e.getMessage());
            throw new RuntimeException("이벤트 직렬화 실패", e);
        }
    }
}

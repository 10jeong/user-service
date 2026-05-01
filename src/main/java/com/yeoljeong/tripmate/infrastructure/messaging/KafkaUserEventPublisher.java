package com.yeoljeong.tripmate.infrastructure.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yeoljeong.tripmate.application.port.UserEventPublisher;
import com.yeoljeong.tripmate.event.UserCreatedEvent;
import com.yeoljeong.tripmate.event.enums.UserTopic;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaUserEventPublisher implements UserEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void publish(UserCreatedEvent event) {
        try {
            String message = objectMapper.writeValueAsString(event);
            kafkaTemplate.send(UserTopic.USER_CREATED_TOPIC, event);
            log.info("UserCreatedEvent published: {}", message);
        } catch (JsonProcessingException e) {
            log.error("UserCreatedEvent 직렬화 실패: {}", e.getMessage());
            throw new RuntimeException("이벤트 직렬화 실패", e);
        }
    }
}

package com.yeoljeong.tripmate.infrastructure.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yeoljeong.tripmate.application.port.UserMessagePort;
import com.yeoljeong.tripmate.event.UserLogoutEvent;
import com.yeoljeong.tripmate.event.enums.UserTopic;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaEventPublisher implements UserMessagePort {

    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void publish(UserLogoutEvent event) {
        try {
            String payload = objectMapper.writeValueAsString(event);
            kafkaTemplate.send(UserTopic.USER_LOGOUT_TOPIC, payload);
            log.info("UserLogoutEvent published: {}", payload);
        } catch (JsonProcessingException e) {
            log.error("UserLogoutEvent 직렬화 실패: {}", e.getMessage());
            throw new RuntimeException("이벤트 직렬화 실패", e);
        }

    }
}

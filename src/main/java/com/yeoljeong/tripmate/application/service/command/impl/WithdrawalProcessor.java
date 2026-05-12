package com.yeoljeong.tripmate.application.service.command.impl;

import com.yeoljeong.tripmate.application.event.UserWithdrawnEvent;
import com.yeoljeong.tripmate.application.port.PasswordEncoderPort;
import com.yeoljeong.tripmate.domain.exception.UserErrorCode;
import com.yeoljeong.tripmate.domain.model.User;
import com.yeoljeong.tripmate.domain.repository.UserRepository;
import com.yeoljeong.tripmate.exception.BusinessException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class WithdrawalProcessor {

    private final UserRepository userRepository;
    private final PasswordEncoderPort passwordEncoderPort;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public void process(UUID userId) {
        User user = getUser(userId);

        String anonymizedPassword = passwordEncoderPort.encode(UUID.randomUUID().toString());
        user.withdraw(anonymizedPassword);
        applicationEventPublisher.publishEvent(new UserWithdrawnEvent(user.getId()));
        // TODO: userEventPublisher.publish(new UserWithdrawalEvent(userId, role));
    }

    // helper method
    private User getUser(UUID userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new BusinessException(UserErrorCode.NOT_FOUND_USER));
    }
}

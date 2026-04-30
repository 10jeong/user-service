package com.yeoljeong.tripmate.application.service.command.impl;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.yeoljeong.tripmate.application.dto.command.UserCreateCommand;
import com.yeoljeong.tripmate.application.dto.result.UserCreateResult;
import com.yeoljeong.tripmate.application.event.UserCreatedEvent;
import com.yeoljeong.tripmate.application.port.UserEventPublisher;
import com.yeoljeong.tripmate.application.service.command.UserCommandService;
import com.yeoljeong.tripmate.domain.exception.UserErrorCode;
import com.yeoljeong.tripmate.domain.model.User;
import com.yeoljeong.tripmate.domain.repository.UserRepository;
import com.yeoljeong.tripmate.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final UserEventPublisher userEventPublisher;

    @Override
    @Transactional
    public UserCreateResult registerUser(UserCreateCommand command) {

        validateCheckByEmail(command.email());

        String encodedPassword = BCrypt.withDefaults()
            .hashToString(12, command.password().toCharArray());

        User user = User.create(command.email(), command.name(), encodedPassword,
            command.gender(),
            command.birthDate(), command.role());

        // 동시에 들어오는 요청이 있을 경우 마지막에 들어오는 요청이 5xx에러가 발생하는 문제가 발생 -> DataIntegrityViolationException를 통해 409로 응답
        try {
            User savedUser = userRepository.save(user);
            userEventPublisher.publish(UserCreatedEvent.from(savedUser));
            return UserCreateResult.from(savedUser);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException(UserErrorCode.ALREADY_EXIST_EMAIL);
        }
    }

    // helper method
    private void validateCheckByEmail(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new BusinessException(UserErrorCode.ALREADY_EXIST_EMAIL);
        }
    }
}

package com.yeoljeong.tripmate.application.service.command.impl;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.yeoljeong.tripmate.application.dto.command.UserCreateCommand;

import com.yeoljeong.tripmate.application.dto.result.UserCreateResult;
import com.yeoljeong.tripmate.application.service.command.UserCommandService;
import com.yeoljeong.tripmate.domain.exception.UserErrorCode;
import com.yeoljeong.tripmate.domain.model.User;
import com.yeoljeong.tripmate.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import com.yeoljeong.tripmate.exception.ApiException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserCreateResult registerUser(UserCreateCommand command) {

        validateCheckByEmail(command.email());

        String encodedPassword = BCrypt.withDefaults()
            .hashToString(12, command.password().toCharArray());

        User user = User.create(command.email(), command.name(), encodedPassword,
            command.gender(),
            command.birthDate(), command.role());

        User savedUser = userRepository.save(user);
        return UserCreateResult.from(savedUser);
    }

    // helper method
    private void validateCheckByEmail(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new ApiException(UserErrorCode.ALREADY_EXIST_EMAIL);
        }
    }
}

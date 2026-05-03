package com.yeoljeong.tripmate.application.service.query.impl;

import com.yeoljeong.tripmate.application.dto.result.UserDetailsResult;
import com.yeoljeong.tripmate.application.service.query.UserQueryService;
import com.yeoljeong.tripmate.domain.exception.UserErrorCode;
import com.yeoljeong.tripmate.domain.model.User;
import com.yeoljeong.tripmate.domain.repository.UserRepository;
import com.yeoljeong.tripmate.exception.BusinessException;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserQueryServiceImpl implements UserQueryService {

    private final UserRepository userRepository;

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findByUserId(UUID userId) {
        return userRepository.findById(userId);
    }

    @Override
    public UserDetailsResult getUserDetails(UUID userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new BusinessException(UserErrorCode.NOT_FOUND_USER));

        return UserDetailsResult.from(user);
    }
}

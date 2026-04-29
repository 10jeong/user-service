package com.yeoljeong.tripmate.application.service.query.impl;

import com.yeoljeong.tripmate.application.service.query.UserQueryService;
import com.yeoljeong.tripmate.domain.model.User;
import com.yeoljeong.tripmate.domain.repository.UserRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
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
}

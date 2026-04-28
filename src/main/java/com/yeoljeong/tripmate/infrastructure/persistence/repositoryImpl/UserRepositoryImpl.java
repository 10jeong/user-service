package com.yeoljeong.tripmate.infrastructure.persistence.repositoryImpl;

import com.yeoljeong.tripmate.domain.model.User;
import com.yeoljeong.tripmate.domain.repository.UserRepository;
import com.yeoljeong.tripmate.infrastructure.persistence.jpa.UserJpaRepository;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public User save(User user) {
        return userJpaRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email);
    }
}

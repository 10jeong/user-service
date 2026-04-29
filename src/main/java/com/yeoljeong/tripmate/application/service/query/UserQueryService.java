package com.yeoljeong.tripmate.application.service.query;

import com.yeoljeong.tripmate.domain.model.User;
import java.util.Optional;

public interface UserQueryService {

    Optional<User> findByEmail(String email);

}

package com.yeoljeong.tripmate.application.service.query;

import com.yeoljeong.tripmate.application.dto.command.UserSummaryCommand;
import com.yeoljeong.tripmate.application.dto.result.UserDetailsResult;
import com.yeoljeong.tripmate.application.dto.result.UserSummaryResult;
import com.yeoljeong.tripmate.domain.model.User;
import com.yeoljeong.tripmate.presentation.dto.response.UserSummaryResponse;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserQueryService {

    Optional<User> findByEmail(String email);

    Optional<User> findByUserId(UUID userId);

    UserDetailsResult getUserDetails(UUID userId);

    List<UserSummaryResult> getUsersByIds(UserSummaryCommand command);
}

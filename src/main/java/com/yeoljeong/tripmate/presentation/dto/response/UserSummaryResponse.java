package com.yeoljeong.tripmate.presentation.dto.response;

import com.yeoljeong.tripmate.application.dto.result.UserSummaryResult;
import java.util.List;
import java.util.UUID;

public record UserSummaryResponse(UUID id, String email, String name) {

    public static List<UserSummaryResponse> from(List<UserSummaryResult> results) {
        return results.stream()
            .map(result -> new UserSummaryResponse(result.id(), result.email(), result.name()))
            .toList();
    }
}

package com.yeoljeong.tripmate.presentation.dto.request;

import com.yeoljeong.tripmate.application.dto.command.UserSummaryCommand;
import java.util.List;
import java.util.UUID;

public record UserSummaryRequest(List<UUID> userIds) {

    public UserSummaryCommand toCommand() {
        return new UserSummaryCommand(userIds);
    }
}

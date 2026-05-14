package com.yeoljeong.tripmate.application.dto.command;

import java.util.List;
import java.util.UUID;

public record UserSummaryCommand(List<UUID> userIds) {

}

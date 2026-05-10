package com.yeoljeong.tripmate.application.port;

import com.yeoljeong.tripmate.application.port.dto.result.WithdrawalCheckResult;
import java.util.UUID;

public interface PlanWithdrawalCheckPort {
    WithdrawalCheckResult check(UUID userId);



}

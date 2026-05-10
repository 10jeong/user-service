package com.yeoljeong.tripmate.infrastructure.client;

import com.yeoljeong.tripmate.application.port.MatchingWithdrawalCheckPort;
import com.yeoljeong.tripmate.application.port.dto.result.WithdrawalCheckResult;
import com.yeoljeong.tripmate.infrastructure.client.dto.WithdrawalCheckResponse;
import com.yeoljeong.tripmate.infrastructure.client.feign.MatchingFeignClient;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MatchingWithdrawalCheckAdapter implements MatchingWithdrawalCheckPort {

    private final MatchingFeignClient feignClient;

    @Override
    public WithdrawalCheckResult check(UUID userId) {
        WithdrawalCheckResponse response = feignClient.check(userId);
        return new WithdrawalCheckResult(response.hasActiveData());
    }
}

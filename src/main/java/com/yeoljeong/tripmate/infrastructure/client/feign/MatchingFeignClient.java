package com.yeoljeong.tripmate.infrastructure.client.feign;

import com.yeoljeong.tripmate.infrastructure.client.dto.WithdrawalCheckResponse;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "matching-service")
public interface MatchingFeignClient {

    @GetMapping("/internal/matchings/withdrawal-check")
    WithdrawalCheckResponse check(@RequestParam("userId") UUID userId);
}

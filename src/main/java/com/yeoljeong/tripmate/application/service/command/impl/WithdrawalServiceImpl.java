package com.yeoljeong.tripmate.application.service.command.impl;

import com.yeoljeong.tripmate.application.port.OrderWithdrawalCheckPort;
import com.yeoljeong.tripmate.application.port.PlanWithdrawalCheckPort;
import com.yeoljeong.tripmate.application.port.SellerWithdrawalCheckPort;
import com.yeoljeong.tripmate.application.port.dto.result.WithdrawalCheckResult;
import com.yeoljeong.tripmate.application.service.command.WithdrawalService;
import com.yeoljeong.tripmate.domain.enums.UserRole;
import com.yeoljeong.tripmate.domain.exception.UserErrorCode;
import com.yeoljeong.tripmate.exception.BusinessException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WithdrawalServiceImpl implements WithdrawalService {

    private final PlanWithdrawalCheckPort planWithdrawalCheckPort;
    private final OrderWithdrawalCheckPort orderWithdrawalCheckPort;
    private final SellerWithdrawalCheckPort sellerWithdrawalCheckPort;
    private final WithdrawalProcessor withdrawalProcessor;


    @Override
    public void withdraw(UUID userId, String role) {
        List<WithdrawalCheckResult> results = new ArrayList<>();
        results.add(planWithdrawalCheckPort.check(userId));
        results.add(orderWithdrawalCheckPort.check(userId));
        //TODO results.add(matchingWithdrawalCheckPort.check(userId));

        if (UserRole.SELLER == UserRole.valueOf(role)) {
            results.add(sellerWithdrawalCheckPort.check(userId));
        }

        // 하나라도 true라면 즉시 true를 반환합니다.
        boolean canNotWithdraw = results.stream()
            .anyMatch(WithdrawalCheckResult::hasActiveData);

        if (canNotWithdraw) {
            throw new BusinessException(UserErrorCode.HAS_ACTIVE_DATA);
        }

        withdrawalProcessor.process(userId);
    }

}

package com.yeoljeong.tripmate.application.service.command.impl;

import com.yeoljeong.tripmate.application.port.MatchingWithdrawalCheckPort;
import com.yeoljeong.tripmate.application.port.OrderWithdrawalCheckPort;
import com.yeoljeong.tripmate.application.port.PasswordEncoderPort;
import com.yeoljeong.tripmate.application.port.PlanWithdrawalCheckPort;
import com.yeoljeong.tripmate.application.port.SellerWithdrawalCheckPort;
import com.yeoljeong.tripmate.application.port.UserEventPublisher;
import com.yeoljeong.tripmate.application.port.dto.result.WithdrawalCheckResult;
import com.yeoljeong.tripmate.application.service.command.WithdrawalService;
import com.yeoljeong.tripmate.domain.enums.UserRole;
import com.yeoljeong.tripmate.domain.exception.UserErrorCode;
import com.yeoljeong.tripmate.domain.model.User;
import com.yeoljeong.tripmate.domain.repository.UserRepository;
import com.yeoljeong.tripmate.exception.BusinessException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WithdrawalServiceImpl implements WithdrawalService {

    private final UserRepository userRepository;
    private final UserEventPublisher userEventPublisher;
    private final PasswordEncoderPort passwordEncoderPort;
    private final PlanWithdrawalCheckPort planWithdrawalCheckPort;
    private final OrderWithdrawalCheckPort orderWithdrawalCheckPort;
    private final MatchingWithdrawalCheckPort matchingWithdrawalCheckPort;
    private final SellerWithdrawalCheckPort sellerWithdrawalCheckPort;

    @Override
    @Transactional
    public void withdraw(UUID userId) {
        User user = getUser(userId);

        List<WithdrawalCheckResult> results = new ArrayList<>();
        results.add(planWithdrawalCheckPort.check(userId));
        results.add(orderWithdrawalCheckPort.check(userId));
        results.add(matchingWithdrawalCheckPort.check(userId));

        if (user.getRole() == UserRole.SELLER) {
            results.add(sellerWithdrawalCheckPort.check(userId));
        }

        // 하나라도 true라면 즉시 true를 반환합니다.
        boolean canNotWithdraw = results.stream()
            .anyMatch(WithdrawalCheckResult::hasActiveData);

        if (canNotWithdraw) {
            throw new BusinessException(UserErrorCode.HAS_ACTIVE_DATA);
        }

        String anonymizedPassword = passwordEncoderPort.encode((UUID.randomUUID().toString()));
        user.withdraw(anonymizedPassword);

        // TODO: userEventPublisher.publish(new UserWithdrawalEvent(userId, role));
    }

    // helper method
    private User getUser(UUID userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new BusinessException(UserErrorCode.NOT_FOUND_USER));
    }
}

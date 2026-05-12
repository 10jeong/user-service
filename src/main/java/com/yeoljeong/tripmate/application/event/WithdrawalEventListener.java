package com.yeoljeong.tripmate.application.event;

import com.yeoljeong.tripmate.application.port.JwtPort;
import com.yeoljeong.tripmate.application.port.RefreshTokenPort;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class WithdrawalEventListener {

    private final RefreshTokenPort refreshTokenPort;
    private final JwtPort jwtPort;

    // DB 커밋 완료 후에 Redis작업을 실행함으로써 원자성을 보장합니다.
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(UserWithdrawnEvent event) {
        String userId = event.userId().toString();
        long ttl = jwtPort.getRefreshExpiration();

        refreshTokenPort.deleteRefreshToken(userId);
        refreshTokenPort.addToBlacklist(userId, ttl, TimeUnit.MILLISECONDS);
    }
}

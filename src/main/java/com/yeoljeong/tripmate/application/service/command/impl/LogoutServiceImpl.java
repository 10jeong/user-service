package com.yeoljeong.tripmate.application.service.command.impl;

import com.yeoljeong.tripmate.application.port.JwtPort;
import com.yeoljeong.tripmate.application.port.RefreshTokenPort;
import com.yeoljeong.tripmate.application.port.UserMessagePort;
import com.yeoljeong.tripmate.application.service.command.LogoutService;
import com.yeoljeong.tripmate.event.UserLogoutEvent;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutServiceImpl implements LogoutService {

    private final RefreshTokenPort refreshTokenPort;
    private final JwtPort jwtPort;
    private final UserMessagePort userMessagePort;

    @Override
    public void logout(UUID userId) {
        String userIdStr = userId.toString();
        long ttl = jwtPort.getAccessTokenExpiration();

        refreshTokenPort.deleteRefreshToken(userIdStr);
        refreshTokenPort.addToBlacklist(userIdStr, ttl, TimeUnit.MILLISECONDS);

        userMessagePort.publish(new UserLogoutEvent(userId));
    }
}

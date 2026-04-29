package com.yeoljeong.tripmate.application.service.auth.impl;

import com.yeoljeong.tripmate.application.dto.command.RefreshTokenCommand;
import com.yeoljeong.tripmate.application.dto.result.LoginCommand;
import com.yeoljeong.tripmate.application.dto.result.LoginResult;
import com.yeoljeong.tripmate.application.port.JwtPort;
import com.yeoljeong.tripmate.application.port.RefreshTokenPort;
import com.yeoljeong.tripmate.application.service.auth.AuthService;
import com.yeoljeong.tripmate.application.service.query.UserQueryService;
import com.yeoljeong.tripmate.domain.exception.UserErrorCode;
import com.yeoljeong.tripmate.domain.model.User;
import com.yeoljeong.tripmate.exception.BusinessException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserQueryService userQueryService;
    private final JwtPort jwtPort;
    private final RefreshTokenPort refreshTokenPort;

    @Override
    public LoginResult login(LoginCommand command) {
        User user = userQueryService.findByEmail(command.email())
            .orElseThrow(() -> new BusinessException(UserErrorCode.NOT_FOUND_USER));

        if (!user.matchPassword(command.password())) {
            throw new BusinessException(UserErrorCode.NOT_FOUND_USER);
        }

        return issueTokens(user);
    }

    @Override
    public LoginResult refreshToken(RefreshTokenCommand command) {
        String refreshToken = command.refreshToken();

        if (!jwtPort.validateToken(refreshToken)) {
            throw new BusinessException(UserErrorCode.INVALID_TOKEN);
        }

        String userId = jwtPort.getUserId(refreshToken);
        String savedToken = refreshTokenPort.getRefreshToken(userId);

        // 불일치 시 탈취 의심에서 예외시키기 위해 요청토큰과 Redis에서 추출된 토큰을 검증합니다.
        if (savedToken == null || !savedToken.equals(refreshToken)) {
            throw new BusinessException(UserErrorCode.INVALID_TOKEN);
        }

        // 기존의 토큰을 무효화 합니다.
        refreshTokenPort.deleteRefreshToken(userId);

        User user = userQueryService.findByUserId(UUID.fromString(userId))
            .orElseThrow(() -> new BusinessException(UserErrorCode.NOT_FOUND_USER));

        return issueTokens(user);
    }

    private @NonNull LoginResult issueTokens(User user) {
        UUID userUUID = user.getId();
        String role = String.valueOf(user.getRole());

        String newAccessToken = jwtPort.generateAccessToken(userUUID, role);
        String newRefreshToken = jwtPort.generateRefreshToken(userUUID);

        //TODO SA문서에 다중기기에 관한 세션 관리 내용이 있으나, MVP개발 단계에서는 단일 기기만 고려
        refreshTokenPort.saveRefreshToken(String.valueOf(user.getId()), newRefreshToken,
            jwtPort.getRefreshExpiration(),
            TimeUnit.MILLISECONDS);

        return new LoginResult(newAccessToken, newRefreshToken, "Bearer",
            jwtPort.getAccessTokenExpiration());
    }
}

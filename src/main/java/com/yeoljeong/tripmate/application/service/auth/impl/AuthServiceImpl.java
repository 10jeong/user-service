package com.yeoljeong.tripmate.application.service.auth.impl;

import com.yeoljeong.tripmate.application.dto.result.LoginCommand;
import com.yeoljeong.tripmate.application.dto.result.LoginResult;
import com.yeoljeong.tripmate.application.port.JwtPort;
import com.yeoljeong.tripmate.application.service.auth.AuthService;
import com.yeoljeong.tripmate.application.service.query.UserQueryService;
import com.yeoljeong.tripmate.domain.exception.UserErrorCode;
import com.yeoljeong.tripmate.domain.model.User;
import com.yeoljeong.tripmate.exception.BusinessException;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserQueryService userQueryService;
    private final JwtPort jwtPort;
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public LoginResult login(LoginCommand command) {
        User user = userQueryService.findByEmail(command.email())
            .orElseThrow(() -> new BusinessException(UserErrorCode.NOT_FOUND_USER));

        if (!isMatch(command.password(), user.getPassword())) {
            throw new BusinessException(UserErrorCode.NOT_FOUND_USER);
        }

        String accessToken = jwtPort.generateAccessToken(user.getId(),
            String.valueOf(user.getRole()));

        String refreshToken = jwtPort.generateRefreshToken(user.getId());

        //TODO SA문서에 다중기기에 관한 세션 관리 내용이 있으나, MVP개발 단계에서는 단일 기기만 고려
        redisTemplate.opsForValue()
            .set("RT:" + user.getId(), refreshToken, jwtPort.getRefreshExpiration(),
                TimeUnit.MILLISECONDS);

        return new LoginResult(accessToken, refreshToken, "Bearer", jwtPort.getAccessTokenExpiration());
    }

    private static boolean isMatch(String rawPassword, String cryptPassword) {
        return BCrypt.verifyer()
            .verify(rawPassword.toCharArray(), cryptPassword)
            .verified;
    }
}

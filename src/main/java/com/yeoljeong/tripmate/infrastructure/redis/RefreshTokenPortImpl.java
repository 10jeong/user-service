package com.yeoljeong.tripmate.infrastructure.redis;

import com.yeoljeong.tripmate.application.port.RefreshTokenPort;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class RefreshTokenPortImpl implements RefreshTokenPort {
    private static final String REFRESH_TOKEN_PREFIX = "RT:";

    private final RedisTemplate<String, String> redisTemplate;

    // TODO: 조회→비교→삭제가 원자적이지 않아 동시 재발급 가능성 있음
    @Override
    public void saveRefreshToken(String userId, String refreshToken, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue()
            .set(REFRESH_TOKEN_PREFIX + userId, refreshToken, timeout,
                unit);
    }

    @Override
    public void deleteRefreshToken(String userId) {
        redisTemplate.delete(REFRESH_TOKEN_PREFIX + userId);

    }

    @Override
    public String getRefreshToken(String userId) {
        return redisTemplate.opsForValue().get(REFRESH_TOKEN_PREFIX + userId);
    }
}

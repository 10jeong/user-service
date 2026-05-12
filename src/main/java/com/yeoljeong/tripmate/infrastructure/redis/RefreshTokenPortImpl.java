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
    private static final String BLACKLIST_PREFIX = "BL:";

    private final RedisTemplate<String, String> redisTemplate;

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
    public String getAndDeleteRefreshToken(String userId) {
        return redisTemplate.opsForValue()
            .getAndDelete(REFRESH_TOKEN_PREFIX + userId);
    }

    @Override
    public void addToBlacklist(String userId, long expiration, TimeUnit unit) {
        redisTemplate.opsForValue()
            .set(BLACKLIST_PREFIX + userId, "blacklisted", expiration,
                unit);
    }

}

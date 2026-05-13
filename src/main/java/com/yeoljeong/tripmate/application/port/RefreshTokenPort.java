package com.yeoljeong.tripmate.application.port;

import java.util.concurrent.TimeUnit;

public interface RefreshTokenPort {

    void saveRefreshToken(String userId, String refreshToken, long timeout, TimeUnit unit);

    void deleteRefreshToken(String userId);

    String getAndDeleteRefreshToken(String userId);

    void addToBlacklist(String userId, long expiration, TimeUnit unit);

    void removeFromBlackList(String userId);
}

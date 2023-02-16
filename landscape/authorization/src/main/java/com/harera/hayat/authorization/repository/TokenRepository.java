package com.harera.hayat.authorization.repository;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TokenRepository extends RedisTemplate<String, Object> {

    private static final String REFRESH_TOKEN = "refresh_token";
    private static final String TOKEN = "token";

    public TokenRepository(RedisConnectionFactory redisConnectionFactory) {
        this.setConnectionFactory(redisConnectionFactory);
    }

    public Long getUserIdForToken(String token) {
        return (Long) this.opsForHash().get(TOKEN, token);
    }

    public Long getUserIdForRefreshToken(String refreshToken) {
        return (Long) this.opsForHash().get(REFRESH_TOKEN, refreshToken);
    }

    public void addToken(long userId, String token) {
        this.opsForHash().put(TOKEN, token, userId);
    }

    public void addRefreshToken(long userId, String token) {
        this.opsForHash().put(REFRESH_TOKEN, token, userId);
    }

    public void removeUserToken(String token) {
        this.opsForHash().delete(TOKEN, token);
    }

    public void removeUserRefreshToken(String refreshToken) {
        this.opsForHash().delete(REFRESH_TOKEN, refreshToken);
    }
}

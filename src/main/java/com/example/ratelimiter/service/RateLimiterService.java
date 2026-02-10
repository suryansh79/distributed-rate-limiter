package com.example.ratelimiter.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class RateLimiterService {

    private final StringRedisTemplate redisTemplate;

    private static final int MAX_REQUESTS = 3;
    private static final long WINDOW_SIZE_MS = 10_000; // 10 seconds

    public RateLimiterService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean isAllowed(String clientId) {
        long now = System.currentTimeMillis();
        String key = "rate_limit:" + clientId;

        // Remove old timestamps
        redisTemplate.opsForZSet()
                .removeRangeByScore(key, 0, now - WINDOW_SIZE_MS);

        // Count requests in window
        Long count = redisTemplate.opsForZSet()
                .zCard(key);

        if (count != null && count >= MAX_REQUESTS) {
            return false;
        }

        // Add current request timestamp
        redisTemplate.opsForZSet()
                .add(key, String.valueOf(now), now);

        // Set expiry so Redis cleans up automatically
        redisTemplate.expire(key, WINDOW_SIZE_MS, TimeUnit.MILLISECONDS);

        return true;
    }
}

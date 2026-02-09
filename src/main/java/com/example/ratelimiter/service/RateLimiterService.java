package com.example.ratelimiter.service;

import java.util.*;

import org.springframework.stereotype.Service;

@Service
public class RateLimiterService {

    private final Map<String, List<Long>> requestMap = new HashMap<>();

    private static final int MAX_REQUESTS = 3;
    private static final long WINDOW_SIZE = 10_000; // 10 seconds

    public synchronized boolean isAllowed(String clientId) {
        long now = System.currentTimeMillis();

        requestMap.putIfAbsent(clientId, new ArrayList<>());
        List<Long> timestamps = requestMap.get(clientId);

        // Remove old requests
        timestamps.removeIf(time -> time < now - WINDOW_SIZE);

        if (timestamps.size() >= MAX_REQUESTS) {
            return false;
        }

        timestamps.add(now);
        return true;
    }
}

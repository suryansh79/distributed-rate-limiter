package com.example.ratelimiter.controller;

import com.example.ratelimiter.dto.RateLimitResponse;
import com.example.ratelimiter.service.RateLimiterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RateLimiterController {

    private final RateLimiterService rateLimiterService;

    public RateLimiterController(RateLimiterService rateLimiterService) {
        this.rateLimiterService = rateLimiterService;
    }

    @GetMapping("/check")
    public ResponseEntity<RateLimitResponse> check(
            @RequestParam String clientId) {

        boolean allowed = rateLimiterService.isAllowed(clientId);

        if (allowed) {
            return ResponseEntity.ok(
                new RateLimitResponse(true, "Request allowed")
            );
        } else {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                .body(new RateLimitResponse(false, "Too many requests"));
        }
    }
}

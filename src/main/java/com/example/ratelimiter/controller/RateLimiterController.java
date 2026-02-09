package com.example.ratelimiter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ratelimiter.service.RateLimiterService;

@RestController
public class RateLimiterController {

    private final RateLimiterService rateLimiterService;

    public RateLimiterController(RateLimiterService rateLimiterService) {
        this.rateLimiterService = rateLimiterService;
    }

    @GetMapping("/check")
    public String check(@RequestParam String clientId) {
        boolean allowed = rateLimiterService.isAllowed(clientId);

        if (allowed) {
            return "Request allowed for client: " + clientId;
        } else {
            return "Too many requests for client: " + clientId;
        }
    }
}

package com.mybrary.backend.global.initializer;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisInitializer implements CommandLineRunner {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void run(String... args) throws Exception {
        redisTemplate.execute((RedisCallback<? extends Object>) connection -> {
            connection.flushAll();
            return null;
        });
    }
}

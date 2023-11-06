package com.example.resilience4jlimitforeachuser.service;

import io.github.resilience4j.common.ratelimiter.configuration.RateLimiterConfigurationProperties;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.autoconfigure.RateLimiterProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import static com.example.resilience4jlimitforeachuser.config.CacheConfig.KEY_RATE_LIMITERS;
import static java.util.Objects.requireNonNull;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheManager = "caffeineCacheManager")
public class RateLimiterManager {
    private final RateLimiterProperties properties;

    @Cacheable(value = KEY_RATE_LIMITERS, key = "#userKey+#rateLimiterName")
    public RateLimiter getLimiter(String userKey, String rateLimiterName) {
        RateLimiterConfigurationProperties.InstanceProperties instanceProperties =
                properties.findRateLimiterProperties(rateLimiterName).orElse(null);

        RateLimiterConfig config = instanceProperties != null ?
                RateLimiterConfig.custom()
                        .timeoutDuration(requireNonNull(instanceProperties.getTimeoutDuration()))
                        .limitRefreshPeriod(requireNonNull(instanceProperties.getLimitRefreshPeriod()))
                        .limitForPeriod(requireNonNull(instanceProperties.getLimitForPeriod()))
                        .build() :
                RateLimiterConfig.ofDefaults();

        return RateLimiter.of(userKey, config);
    }
}

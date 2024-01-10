package com.example.resilience4jlimitforeachuser.controller;

import com.example.resilience4jlimitforeachuser.service.TargetService;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static com.example.resilience4jlimitforeachuser.config.CacheConfig.KEY_RATE_LIMITERS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@EnableCaching
@ActiveProfiles("test")
class TestControllerTest {
    @Autowired
    private TestController testController;
    @Autowired
    private CacheManager cacheManager;
    @MockBean
    private TargetService targetService;
    @Autowired
    private RateLimiterRegistry rateLimiterRegistry;

    @Test
    @DirtiesContext
    void getTestInfo_apply_rateLimiter_for_different_users() {
        int target = 15;
        int limitForPeriod = rateLimiterRegistry
                .rateLimiter("testRateLimiter")
                .getRateLimiterConfig()
                .getLimitForPeriod();

        String anyString = "any String";

        when(targetService.getSomethingUsefulResult(anyString())).thenReturn(anyString);

        LocalDateTime currentTime = LocalDateTime.now();

        int responseCount = 0;
        for (int i = 0; i < target; i++) {
            try {
                assertThrows(RequestNotPermitted.class,
                        () -> testController.getSomethingInfo(111));
            } catch (AssertionFailedError error) {
                System.out.println(currentTime);
                responseCount++;
            }
        }

        assertEquals(target / 5 * limitForPeriod, responseCount);

        Cache cache = cacheManager.getCache(KEY_RATE_LIMITERS);

        assertThat(cache).isNotNull();
    }

}
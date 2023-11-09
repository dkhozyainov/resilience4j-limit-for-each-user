package com.example.resilience4jlimitforeachuser.controller;


import com.example.resilience4jlimitforeachuser.service.RateLimiterRecipientInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/test", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class TestController {
    private final RateLimiterRecipientInfoService rateLimiterRecipientInfoService;

    @GetMapping("/limited")
    public ResponseEntity<String> getSomethingInfo(@RequestParam(value = "length") Integer length) {
        String res = rateLimiterRecipientInfoService.getTestInfo(length);
        return ResponseEntity.ok(res);
    }
}

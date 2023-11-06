package com.example.resilience4jlimitforeachuser.controller;


import com.example.resilience4jlimitforeachuser.service.RateLimiterRecipientInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<String> getRecipientInfo(@RequestParam(value = "length") Integer length) {
        String res = rateLimiterRecipientInfoService.getRecipientInfoByPhone(length);
        return res == null
                ? ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build()
                : ResponseEntity.ok(res);
    }
}

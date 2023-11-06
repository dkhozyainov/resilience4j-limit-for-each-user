package com.example.resilience4jlimitforeachuser.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    public String getRandomUserId() {
        UUID userId = UUID.randomUUID();
        return userId.toString();
    }
}

package com.example.resilience4jlimitforeachuser.service;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    private int counter;
    private String userId = "userId";

    public String getRandomUserId() {
        if (counter % 5 == 0) {
            userId = userId + counter;
        }
        counter++;
        return userId;
    }
}

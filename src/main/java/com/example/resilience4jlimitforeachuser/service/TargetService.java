package com.example.resilience4jlimitforeachuser.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class TargetService {

    private final Random random = new Random();

    public String getSomethingUsefulResult(String s) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(10);

        int randomLength = 10;
        for (int i = 0; i < randomLength; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            sb.append(randomChar);
        }

        return sb.toString();
    }

}

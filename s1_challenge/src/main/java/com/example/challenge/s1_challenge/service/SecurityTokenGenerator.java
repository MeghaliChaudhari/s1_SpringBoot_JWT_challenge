package com.example.challenge.s1_challenge.service;

import com.example.challenge.s1_challenge.domain.User;

import java.util.Map;

public interface SecurityTokenGenerator {
    Map<String,String> generateToken(User user);
}

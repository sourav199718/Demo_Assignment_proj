package com.example.newapp.service;

import com.example.newapp.dto.AuthRequest;
import com.example.newapp.dto.AuthResponse;
import com.example.newapp.exception.InvalidCredentialsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    public AuthResponse login(AuthRequest request) {
        logger.info("Attempting login for user: {}", request.getUsername());

        if ("admin".equals(request.getUsername()) && "password".equals(request.getPassword())) {
            logger.info("Login successful for user: {}", request.getUsername());
            return new AuthResponse("jwt-token-123");
        } else {
            logger.warn("Invalid login attempt for user: {}", request.getUsername());
            throw new InvalidCredentialsException("Invalid username or password");
        }
    }
}

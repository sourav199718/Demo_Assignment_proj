package com.example.newapp.service;

import com.example.newapp.dto.AuthRequest;
import com.example.newapp.dto.AuthResponse;
import com.example.newapp.exception.InvalidCredentialsException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthServiceTest {

    private final AuthService authService = new AuthService();

    @Test
    void loginSuccessReturnsToken() {
        AuthRequest request = new AuthRequest("admin", "password");
        AuthResponse response = authService.login(request);
        assertNotNull(response);
        assertEquals("jwt-token-123", response.getToken());
    }

    @Test
    void loginFailsThrowsInvalidCredentialsException() {
        AuthRequest request = new AuthRequest("user", "wrongpass");
        InvalidCredentialsException exception = assertThrows(
                InvalidCredentialsException.class,
                () -> authService.login(request)
        );
        assertEquals("Invalid username or password", exception.getMessage());
    }
}

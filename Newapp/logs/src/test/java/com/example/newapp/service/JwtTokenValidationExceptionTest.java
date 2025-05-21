package com.example.newapp.service;

import com.example.newapp.exception.JwtTokenValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenValidationExceptionTest {

    @Test
    @DisplayName("Test default constructor")
    void testDefaultConstructor() {
        JwtTokenValidationException ex = new JwtTokenValidationException();
        assertNull(ex.getMessage(), "Message should be null for default constructor");
        assertNull(ex.getCause(), "Cause should be null for default constructor");
    }

    @Test
    @DisplayName("Test constructor with message only")
    void testMessageConstructor() {
        String message = "Token is invalid";
        JwtTokenValidationException ex = new JwtTokenValidationException(message);
        assertEquals(message, ex.getMessage(), "Message should match");
        assertNull(ex.getCause(), "Cause should be null when only message is passed");
    }

    @Test
    @DisplayName("Test constructor with cause only")
    void testCauseConstructor() {
        Throwable cause = new IllegalArgumentException("Underlying error");
        JwtTokenValidationException ex = new JwtTokenValidationException(cause);
        assertEquals("java.lang.IllegalArgumentException: Underlying error", ex.getMessage(), "Message should match cause's toString()");
        assertEquals(cause, ex.getCause(), "Cause should match");
    }

    @Test
    @DisplayName("Test constructor with message and cause")
    void testMessageAndCauseConstructor() {
        String message = "Signature mismatch";
        Throwable cause = new RuntimeException("Cause details");
        JwtTokenValidationException ex = new JwtTokenValidationException(message, cause);
        assertEquals(message, ex.getMessage(), "Message should match");
        assertEquals(cause, ex.getCause(), "Cause should match");
    }

    @Test
    @DisplayName("Test full constructor with suppression and writableStackTrace")
    void testFullConstructor() {
        String message = "Full constructor used";
        Throwable cause = new Exception("Deep cause");
        boolean enableSuppression = false;
        boolean writableStackTrace = false;

        JwtTokenValidationException ex = new JwtTokenValidationException(
                message, cause, enableSuppression, writableStackTrace);

        assertEquals(message, ex.getMessage(), "Message should match");
        assertEquals(cause, ex.getCause(), "Cause should match");
    }
}

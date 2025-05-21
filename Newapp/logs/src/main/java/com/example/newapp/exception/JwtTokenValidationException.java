package com.example.newapp.exception;

public class JwtTokenValidationException extends RuntimeException {


    public JwtTokenValidationException() {
        super();
    }


    public JwtTokenValidationException(String message) {
        super(message);
    }


    public JwtTokenValidationException(Throwable cause) {
        super(cause);
    }


    public JwtTokenValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public JwtTokenValidationException(String message, Throwable cause,
                                       boolean enableSuppression,
                                       boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

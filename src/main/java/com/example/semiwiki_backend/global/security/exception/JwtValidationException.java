package com.example.semiwiki_backend.global.security.exception;

public class JwtValidationException extends RuntimeException {
    public JwtValidationException(String message) {
        super(message);
    }
    public JwtValidationException(String message, Throwable cause) {
      super(message, cause);
    }
}

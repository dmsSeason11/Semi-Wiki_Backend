package com.example.semiwiki_backend.global.security.config;

public class JwtExpiredException extends RuntimeException {
  public JwtExpiredException(String message) {
    super(message);
  }
  
}

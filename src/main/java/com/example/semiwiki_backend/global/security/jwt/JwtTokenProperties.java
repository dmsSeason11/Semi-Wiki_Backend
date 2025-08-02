package com.example.semiwiki_backend.global.security.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProperties {
    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.access-exp}")
    private String accessTokenExpiresIn;

    @Value("${jwt.refresh-exp}")
    private String refreshTokenExpiresIn;
}

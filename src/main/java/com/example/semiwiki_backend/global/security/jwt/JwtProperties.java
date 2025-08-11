package com.example.semiwiki_backend.global.security.jwt;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class JwtProperties {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.access-exp}")
    private Long accessTokenExpiresIn;

    @Value("${jwt.refresh-exp}")
    private Long refreshTokenExpiresIn;
}

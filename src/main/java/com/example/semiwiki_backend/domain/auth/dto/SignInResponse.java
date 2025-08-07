package com.example.semiwiki_backend.domain.auth.dto;


import lombok.Getter;

@Getter
public class SignInResponse {
    private String accessToken;
    private String refreshToken;
    private String accessTokenExpiresIn;
    private String refreshTokenExpiresIn;
}

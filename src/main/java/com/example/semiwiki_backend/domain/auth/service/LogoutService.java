package com.example.semiwiki_backend.domain.auth.service;

import com.example.semiwiki_backend.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService {

    private final JwtTokenProvider jwtTokenProvider;

    public void execute(String accountId){
        jwtTokenProvider.deleteRefreshToken(accountId);

    }
}

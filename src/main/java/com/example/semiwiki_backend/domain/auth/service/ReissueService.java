package com.example.semiwiki_backend.domain.auth.service;

import com.example.semiwiki_backend.domain.auth.dto.ReissueRequest;
import com.example.semiwiki_backend.domain.auth.dto.TokenResponse;
import com.example.semiwiki_backend.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReissueService {

    private final JwtTokenProvider jwtTokenProvider;

    public TokenResponse execute(ReissueRequest  reissueRequest) {

        String accountId = reissueRequest.getAccountId();
        String refreshToken = reissueRequest.getRefreshToken();

        String newAccessToken = jwtTokenProvider.reissueAccessToken(accountId, refreshToken);

        return new TokenResponse(newAccessToken,refreshToken);
    }
}

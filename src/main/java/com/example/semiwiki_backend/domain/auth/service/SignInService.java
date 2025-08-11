package com.example.semiwiki_backend.domain.auth.service;

import com.example.semiwiki_backend.domain.auth.dto.SignInRequest;
import com.example.semiwiki_backend.domain.auth.dto.TokenResponse;
import com.example.semiwiki_backend.domain.auth.exception.AccountNotFoundException;
import com.example.semiwiki_backend.domain.auth.exception.IncorrectPasswordException;
import com.example.semiwiki_backend.domain.user.entity.User;
import com.example.semiwiki_backend.domain.user.repository.UserRepository;
import com.example.semiwiki_backend.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignInService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public TokenResponse execute(SignInRequest signInRequest) {
        User user =userRepository.findByAccountId(signInRequest.getAccountId())
                .orElseThrow(()-> AccountNotFoundException.EXCEPTION);

        if(!passwordEncoder.matches(signInRequest.getPassword(), user.getPassword())) {
            throw IncorrectPasswordException.EXCEPTION;
        }

        return new TokenResponse(jwtTokenProvider.generateAccessToken(user.getAccountId()));
    }
}


package com.example.semiwiki_backend.domain.auth.service;

import ch.qos.logback.classic.spi.IThrowableProxy;
import com.example.semiwiki_backend.domain.auth.dto.SignInRequest;
import com.example.semiwiki_backend.domain.auth.dto.SignUpRequest;
import com.example.semiwiki_backend.domain.auth.dto.TokenResponse;
import com.example.semiwiki_backend.domain.auth.exception.AccountAlreadyExistsException;
import com.example.semiwiki_backend.domain.auth.exception.AccountNotFoundException;
import com.example.semiwiki_backend.domain.auth.exception.IncorrectPasswordException;
import com.example.semiwiki_backend.domain.user.entity.User;
import com.example.semiwiki_backend.domain.user.repository.UserRepository;
import com.example.semiwiki_backend.global.security.auth.Role;
import com.example.semiwiki_backend.global.security.jwt.JwtTokenProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public TokenResponse join(SignUpRequest signUpRequest) {
        if(userRepository.existsByAccountId(signUpRequest.getAccountId())) {
            throw AccountAlreadyExistsException.EXCEPTION;
        }

        User user=User.builder()
            .accountId(signUpRequest.getAccountId())
            .studentNum(signUpRequest.getStudentNum())
            .username(signUpRequest.getUsername())
            .password(passwordEncoder.encode(signUpRequest.getPassword()))
            .role(Role.ROLE_USER)
            .build();

        userRepository.save(user);

        return new TokenResponse(jwtTokenProvider.generateAccessToken(user.getAccountId()));
    }

    public TokenResponse login(SignInRequest signInRequest) {
        User user =userRepository.findByAccountId(signInRequest.getAccountId())
            .orElseThrow(()-> AccountNotFoundException.EXCEPTION);

        if(!passwordEncoder.matches(signInRequest.getPassword(), user.getPassword())) {
            throw IncorrectPasswordException.EXCEPTION;
        }

        return new TokenResponse(jwtTokenProvider.generateAccessToken(user.getAccountId()));
    }

}

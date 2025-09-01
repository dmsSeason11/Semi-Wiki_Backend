package com.example.semiwiki_backend.domain.auth.service;

import com.example.semiwiki_backend.domain.auth.exception.AccountNotFoundException;
import com.example.semiwiki_backend.domain.user.repository.UserRepository;
import com.example.semiwiki_backend.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    public void execute(String accountId){
        if(userRepository.existsByAccountId(accountId)){
            jwtTokenProvider.deleteRefreshToken(accountId);
        }
        else {
            throw new AccountNotFoundException();
        }


    }
}

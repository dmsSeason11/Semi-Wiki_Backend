package com.example.semiwiki_backend.domain.auth.service;

import com.example.semiwiki_backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@RequiredArgsConstructor
public class CheckAccountIdService {

    private final UserRepository userRepository;

    public boolean execute(String accountId) {
        boolean exists=userRepository.existsByAccountId(accountId);
        return !exists;
    }
}

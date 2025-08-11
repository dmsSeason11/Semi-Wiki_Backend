package com.example.semiwiki_backend.domain.auth.controller;


import com.example.semiwiki_backend.domain.auth.dto.SignInRequest;
import com.example.semiwiki_backend.domain.auth.dto.TokenResponse;
import com.example.semiwiki_backend.domain.auth.dto.SignUpRequest;
import com.example.semiwiki_backend.domain.auth.service.SignInService;
import com.example.semiwiki_backend.domain.auth.service.SignUpService;
import com.example.semiwiki_backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final SignUpService signUpService;
    private final SignInService signInService;
    private final UserRepository userRepository;


    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public TokenResponse signUp(@RequestBody SignUpRequest signUpRequest) {
        return signUpService.execute(signUpRequest);
    }

    @PostMapping("/sign-in")
    @ResponseStatus(HttpStatus.OK)
    public TokenResponse signIn(@RequestBody SignInRequest signInRequest) {
        return signInService.execute(signInRequest);
    }

    //true = 사용 가능, false = 사용 불가(이미 존재)
    @GetMapping("/check-accountId")
    @ResponseStatus(HttpStatus.OK)
    public boolean checkAccountId(@RequestParam String accountId) {
        boolean exists=userRepository.existsByAccountId(accountId);
        return !exists;
    }


}

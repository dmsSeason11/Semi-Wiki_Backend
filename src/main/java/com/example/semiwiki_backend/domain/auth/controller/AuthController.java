package com.example.semiwiki_backend.domain.auth.controller;


import com.example.semiwiki_backend.domain.auth.dto.SignInRequest;
import com.example.semiwiki_backend.domain.auth.dto.TokenResponse;
import com.example.semiwiki_backend.domain.auth.dto.SignUpRequest;
import com.example.semiwiki_backend.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public TokenResponse signUp(@RequestBody SignUpRequest signUpRequest) {
        return authService.join(signUpRequest);
    }

    @PostMapping("/sign-in")
    @ResponseStatus(HttpStatus.OK)
    public TokenResponse signIn(@RequestBody SignInRequest signInRequest) {
        return authService.login(signInRequest);
    }


}

package com.example.semiwiki_backend.domain.auth.controller;


import com.example.semiwiki_backend.domain.auth.dto.ReissueRequest;
import com.example.semiwiki_backend.domain.auth.dto.SignInRequest;
import com.example.semiwiki_backend.domain.auth.dto.TokenResponse;
import com.example.semiwiki_backend.domain.auth.dto.SignUpRequest;
import com.example.semiwiki_backend.domain.auth.service.*;
import com.example.semiwiki_backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.token.TokenService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final SignUpService signUpService;
    private final SignInService signInService;
    private final CheckAccountIdService checkAccountIdService;
    private final ReissueService reissueService;
    private final LogoutService logoutService;



    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public TokenResponse signUp(@RequestBody SignUpRequest signUpRequest) {
        return signUpService.execute(signUpRequest);
    }

    @PostMapping("/signin")
    @ResponseStatus(HttpStatus.OK)
    public TokenResponse signIn(@RequestBody SignInRequest signInRequest) {
        return signInService.execute(signInRequest);
    }

    //true = 사용 가능, false = 사용 불가(이미 존재)
    //수정 필요 account-id
    @GetMapping("/checkaccountid")
    @ResponseStatus(HttpStatus.OK)
    public boolean checkAccountId(@RequestParam String accountId) {
        return checkAccountIdService.execute(accountId);
    }

    @PostMapping("/reissue")
    @ResponseStatus(HttpStatus.OK)
    public TokenResponse reissue(@RequestBody ReissueRequest reissueRequest) {
        return reissueService.execute(reissueRequest);
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public void logout(@RequestParam String accountId) {
        logoutService.execute(accountId);
    }


}

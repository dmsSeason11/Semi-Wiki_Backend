package com.example.semiwiki_backend.domain.auth.controller;


import com.example.semiwiki_backend.domain.auth.dto.ReissueRequest;
import com.example.semiwiki_backend.domain.auth.dto.SignInRequest;
import com.example.semiwiki_backend.domain.auth.dto.TokenResponse;
import com.example.semiwiki_backend.domain.auth.dto.SignUpRequest;
import com.example.semiwiki_backend.domain.auth.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
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
    private final DeleteUserService deleteUserService;


    // 회원가입은 임시적으로 막아둔 상태
    // @PostMapping("/signup")
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
    @GetMapping("/check/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public boolean checkAccountId(@PathVariable String accountId) {
        return checkAccountIdService.execute(accountId);
    }

    @PostMapping("/reissue")
    @ResponseStatus(HttpStatus.OK)
    public TokenResponse reissue(@RequestBody ReissueRequest reissueRequest) {
        return reissueService.execute(reissueRequest);
    }

    @PostMapping("/logout/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public void logout(@PathVariable String accountId) {
        logoutService.execute(accountId);
    }

    @DeleteMapping("{accountId}")
    public void delete(@PathVariable String accountId, Authentication authentication) {
        deleteUserService.execute(accountId,authentication);
    }


}

package com.example.semiwiki_backend.domain.auth.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInRequest {
    private String accountId;
    private String password;
}

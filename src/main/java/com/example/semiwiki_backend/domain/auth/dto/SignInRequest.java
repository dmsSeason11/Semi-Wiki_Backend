package com.example.semiwiki_backend.domain.auth.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
public class SignInRequest {
    private String accountId;
    private String password;
}

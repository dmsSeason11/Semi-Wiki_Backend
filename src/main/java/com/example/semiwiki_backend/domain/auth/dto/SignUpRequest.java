package com.example.semiwiki_backend.domain.auth.dto;


import lombok.Getter;

@Getter
public class SignUpRequest {
    private String accountId;
    private int studentNum;
    private String password;
    private String username;
}

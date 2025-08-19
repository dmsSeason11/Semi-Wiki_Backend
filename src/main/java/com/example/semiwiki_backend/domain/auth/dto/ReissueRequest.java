package com.example.semiwiki_backend.domain.auth.dto;

import lombok.Getter;

@Getter
public class ReissueRequest {
    private String accountId;
    private String refreshToken;
}

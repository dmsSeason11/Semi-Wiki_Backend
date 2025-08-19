package com.example.semiwiki_backend.domain.user.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserMyPageResponseDto {
    private String accountId;
    private int noticeBoardCount;
}

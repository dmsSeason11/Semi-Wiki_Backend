package com.example.semiwiki_backend.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

//글 목록 불러올때 유저 불러오는 dto
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserPreviewResponseDto {
    private int userId;

    private String accountId;
}

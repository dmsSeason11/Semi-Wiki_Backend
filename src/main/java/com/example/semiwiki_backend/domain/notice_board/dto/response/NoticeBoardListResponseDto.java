package com.example.semiwiki_backend.domain.notice_board.dto.response;

import com.example.semiwiki_backend.domain.user.dto.response.UserPreviewResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class NoticeBoardListResponseDto {
    private int id;

    private String title;

    private List<String> categories;

    private UserPreviewResponseDto userPreview;
}

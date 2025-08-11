package com.example.semiwiki_backend.domain.notice_table.dto.response;

import com.example.semiwiki_backend.domain.user.dto.response.UserPreviewResponseDto;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class NoticeTableListResponseDto {
    private int id;

    private String title;

    private List<String> categories;

    private UserPreviewResponseDto userPreview;
}

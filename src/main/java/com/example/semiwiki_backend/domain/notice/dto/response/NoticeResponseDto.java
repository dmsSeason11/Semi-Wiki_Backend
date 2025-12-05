package com.example.semiwiki_backend.domain.notice.dto.response;

import com.example.semiwiki_backend.domain.notice.type.Type;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class NoticeResponseDto {
    private Long id;

    private String title;

    private String contents;

    private Type type;
}

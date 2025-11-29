package com.example.semiwiki_backend.domain.notice.dto.request;

import com.example.semiwiki_backend.domain.notice.type.Type;
import lombok.Getter;

@Getter
public class NoticeUpdateRequestDto {
    private String title;

    private String contents;

    private Type type;
}

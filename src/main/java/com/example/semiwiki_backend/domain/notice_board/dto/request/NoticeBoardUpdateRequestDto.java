package com.example.semiwiki_backend.domain.notice_board.dto.request;

import lombok.*;

import java.util.List;

@Getter
public class NoticeBoardUpdateRequestDto {
    private String title;

    private String contents;

    private List<String> categories;
}

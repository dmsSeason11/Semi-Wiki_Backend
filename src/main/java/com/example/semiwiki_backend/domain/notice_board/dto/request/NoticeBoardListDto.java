package com.example.semiwiki_backend.domain.notice_board.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class NoticeBoardListDto {
    private List<String> categories;

    private String keyword;

    private int offset = 0;

    private int limit = 20;
}

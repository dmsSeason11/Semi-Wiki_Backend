package com.example.semiwiki_backend.domain.notice_board.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class NoticeBoardCountRequestDto {
    private String title;

    private List<String> categories;
}

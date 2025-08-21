package com.example.semiwiki_backend.domain.notice_board.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class NoticeBoardHeaderUpdateRequestDto {
    private String title;

    private String contents;

    private List<String> categories;

    private Long headerId;
}

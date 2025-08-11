package com.example.semiwiki_backend.domain.notice_board.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NoticeBoardCreateRequestDto {
    private String title;

    private String contents;

    private List<String> categories;

    private Integer userId;
}

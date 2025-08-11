package com.example.semiwiki_backend.domain.notice_board.dto.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeBoardUpdateRequest {
    private String title;
    private String contents;
    private Integer userId;
    private List<String> categories;
}

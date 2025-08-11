package com.example.semiwiki_backend.domain.notice_table.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NoticeTableCreateRequestDto {
    private String title;

    private String contents;

    private List<String> categories;

    private Integer userId;
}

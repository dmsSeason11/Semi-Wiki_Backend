package com.example.semiwiki_backend.domain.notice_table.dto.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeTableUpdateRequest {
    private int noticeTableId;
    private String title;
    private String contents;
    private Integer userId;
    private List<String> categories;
}

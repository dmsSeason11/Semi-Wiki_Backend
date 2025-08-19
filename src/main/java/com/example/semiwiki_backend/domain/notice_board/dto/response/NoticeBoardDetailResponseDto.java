package com.example.semiwiki_backend.domain.notice_board.dto.response;

import com.example.semiwiki_backend.domain.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class NoticeBoardDetailResponseDto {
    private String title;

    private String contents;

    private LocalDateTime createdAt;

    private LocalDateTime modficatedAt;

    @JsonIgnoreProperties({"noticeBoards", "password"})
    private List<User> users;

    private List<String> categories;
}

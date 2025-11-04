package com.example.semiwiki_backend.domain.comment.dto.response;

import com.example.semiwiki_backend.domain.notice_board.entity.NoticeBoard;
import com.example.semiwiki_backend.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class CommentDetailResponseDto {
    private LocalDateTime wroteAt;

    private LocalDateTime modificatedAt;

    private String contents;

    private String accountId;
}

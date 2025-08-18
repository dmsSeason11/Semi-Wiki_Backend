package com.example.semiwiki_backend.domain.user_notice_board.entity;

import com.example.semiwiki_backend.domain.notice_board.entity.NoticeBoard;
import com.example.semiwiki_backend.domain.user.entity.User;
import com.example.semiwiki_backend.domain.user_notice_board.entity.type.UserNoticeBoardId;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@IdClass(UserNoticeBoardId.class)
public class UserNoticeBoard {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties("noticeBoards")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "notice_board_id")
    @JsonIgnoreProperties("users")
    private NoticeBoard noticeBoard;
}

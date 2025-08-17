package com.example.semiwiki_backend.domain.like.entity;

import com.example.semiwiki_backend.domain.like.entity.type.LikeId;
import com.example.semiwiki_backend.domain.notice_board.entity.NoticeBoard;
import com.example.semiwiki_backend.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(LikeId.class)
@Entity
public class Like {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "notice_board_id")
    private NoticeBoard noticeBoard;
}

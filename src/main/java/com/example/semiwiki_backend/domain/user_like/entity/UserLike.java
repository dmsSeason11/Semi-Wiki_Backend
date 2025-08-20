package com.example.semiwiki_backend.domain.user_like.entity;

import com.example.semiwiki_backend.domain.user_like.entity.type.UserLikeId;
import com.example.semiwiki_backend.domain.notice_board.entity.NoticeBoard;
import com.example.semiwiki_backend.domain.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(UserLikeId.class)
@Entity
public class UserLike {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"noticeBoards", "password"})
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "notice_board_id")
    @JsonIgnoreProperties({"users", "password"})
    @OnDelete(action = OnDeleteAction.CASCADE)
    private NoticeBoard noticeBoard;
}

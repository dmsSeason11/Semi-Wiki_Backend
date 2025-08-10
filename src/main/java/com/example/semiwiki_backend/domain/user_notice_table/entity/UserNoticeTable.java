package com.example.semiwiki_backend.domain.user_notice_table.entity;

import com.example.semiwiki_backend.domain.user.entity.User;
import com.example.semiwiki_backend.domain.user_notice_table.entity.type.UserNoticeTableId;
import jakarta.persistence.*;

@Entity
@IdClass(UserNoticeTableId.class)
public class UserNoticeTable {
    @Id
    @Column(name = "user_id")
    private Integer userId;

    @Id
    @Column(name = "notice_board_id")
    private Integer noticeBoardId;
}

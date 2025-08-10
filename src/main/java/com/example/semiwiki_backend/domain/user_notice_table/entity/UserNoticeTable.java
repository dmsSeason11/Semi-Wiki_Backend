package com.example.semiwiki_backend.domain.user_notice_table.entity;

import com.example.semiwiki_backend.domain.notice_table.entity.NoticeTable;
import com.example.semiwiki_backend.domain.user.entity.User;
import com.example.semiwiki_backend.domain.user_notice_table.entity.type.UserNoticeTableId;
import jakarta.persistence.*;

@Entity
@IdClass(UserNoticeTableId.class)
public class UserNoticeTable {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "notice_board_id")
    private NoticeTable noticeBoard;
}

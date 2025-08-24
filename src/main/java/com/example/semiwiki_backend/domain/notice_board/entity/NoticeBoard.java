package com.example.semiwiki_backend.domain.notice_board.entity;

import com.example.semiwiki_backend.domain.user_notice_board.entity.UserNoticeBoard;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class NoticeBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int id;

    @Column(nullable = false)
    private String title;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "modificated_at")
    private LocalDateTime modficatedAt;

    @OneToMany(mappedBy = "noticeBoard", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties({"user"})
    private List<UserNoticeBoard> users = new ArrayList<>();

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="notice_board_id")
    private List<NoticeBoardHeader> noticeBoardHeaders = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "NoticeBoardCategory", joinColumns = @JoinColumn(name = "notice_board_id"))
    @Column(name = "category")
    private List<String> categories = new ArrayList<>();

    public void addUserNotice(UserNoticeBoard userNoticeBoard) {
        if(this.users == null) {
            this.users = new ArrayList<>();
        }
        this.users.add(userNoticeBoard);
    }

    public void updateUserNoticeAndCategories(List<UserNoticeBoard> userNoticeBoard,List<String> categories) {
        this.users = userNoticeBoard;
        this.categories = categories;
    }
}

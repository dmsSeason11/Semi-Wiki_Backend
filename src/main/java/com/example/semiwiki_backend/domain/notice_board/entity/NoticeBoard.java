package com.example.semiwiki_backend.domain.notice_board.entity;

import com.example.semiwiki_backend.domain.user_notice_board.entity.UserNoticeBoard;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Column(nullable = false)
    private String contents;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "modificated_at")
    private LocalDateTime modficatedAt;

    @OneToMany(mappedBy = "noticeBoard", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties({"user"})
    private List<UserNoticeBoard> users = new ArrayList<>();

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
}

package com.example.semiwiki_backend.domain.notice_board.entity;

import com.example.semiwiki_backend.domain.comment.entity.Comment;
import com.example.semiwiki_backend.domain.user_notice_board.entity.UserNoticeBoard;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false,columnDefinition = "LONGTEXT")
    @Lob
    private String contents;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "modificated_at")
    private LocalDateTime modficatedAt;

    @OneToMany(mappedBy = "noticeBoard", cascade = CascadeType.REMOVE,fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"user"})
    private List<UserNoticeBoard> users = new ArrayList<>();

    @OneToMany(cascade=CascadeType.ALL, orphanRemoval=true)
    @JoinColumn(name="notice_board_id")
    private List<NoticeBoardHeader> noticeBoardHeaders = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "NoticeBoardCategory", joinColumns = @JoinColumn(name = "notice_board_id"))
    @Column(name = "category")
    private List<String> categories = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    public void addUserNotice(UserNoticeBoard userNoticeBoard) {
        if(this.users == null) {
            this.users = new ArrayList<>();
        }
        this.users.add(userNoticeBoard);
    }

    public void updateHeaderEmpty(){
        this.noticeBoardHeaders.clear(); // 엔티티 삭제 위함
    }

    public void updateNoticeBoard(List<NoticeBoardHeader> headers, List<UserNoticeBoard> users, String title, String contents, List<String> categories) {
        this.noticeBoardHeaders.addAll(headers);
        this.contents = contents;
        this.categories = categories;
        this.title = title;
        this.users = users;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }
}

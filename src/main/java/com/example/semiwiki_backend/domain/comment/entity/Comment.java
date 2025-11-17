package com.example.semiwiki_backend.domain.comment.entity;

import com.example.semiwiki_backend.domain.notice_board.entity.NoticeBoard;
import com.example.semiwiki_backend.domain.user.entity.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @CreationTimestamp
    private LocalDateTime wroteAt;

    @UpdateTimestamp
    private LocalDateTime modificatedAt;

    @Column(nullable = false)
    private String contents;

    @ManyToOne
    @JoinColumn(name = "notice_board_id")
    @JsonIgnore
    private NoticeBoard noticeBoard;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void updateContents(String contents) {
        this.contents = contents;
    }

    public Boolean isValidComment(){
        int countLineBreak = this.contents.length() - this.contents.replace(String.valueOf('\n'),"").length();
        return !(countLineBreak > 10); // 줄넘김이 10개 이상이 아닌지 반환
    }
}

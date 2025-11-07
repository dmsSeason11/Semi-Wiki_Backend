package com.example.semiwiki_backend.domain.notice_board.entity;

import com.example.semiwiki_backend.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Image {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  private Long id;

  @JoinColumn(name ="user_id")
  @ManyToOne
  private User user;

  @JoinColumn(name = "notice_board_id")
  @ManyToOne
  private NoticeBoard noticeBoard;

  @Column(nullable = false)
  private String fileName;

  @Column(nullable = false)
  private String url;

  @Column(nullable = false)
  private LocalDateTime createdAt;

  public void assignTo(NoticeBoard noticeBoard) {
    this.noticeBoard = noticeBoard;
  }
}

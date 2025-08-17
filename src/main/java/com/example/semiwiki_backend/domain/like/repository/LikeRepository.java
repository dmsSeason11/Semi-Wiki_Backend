package com.example.semiwiki_backend.domain.like.repository;

import com.example.semiwiki_backend.domain.like.entity.Like;
import com.example.semiwiki_backend.domain.notice_board.entity.NoticeBoard;
import com.example.semiwiki_backend.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    public Like findByUserAndNoticeBoard(User user, NoticeBoard noticeBoard);
    public Integer countAllByNoticeBoard(NoticeBoard noticeBoard);
}

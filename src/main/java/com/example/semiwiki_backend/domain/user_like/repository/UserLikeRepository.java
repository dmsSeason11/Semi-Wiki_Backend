package com.example.semiwiki_backend.domain.user_like.repository;

import com.example.semiwiki_backend.domain.user_like.entity.UserLike;
import com.example.semiwiki_backend.domain.notice_board.entity.NoticeBoard;
import com.example.semiwiki_backend.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLikeRepository extends JpaRepository<UserLike, Long> {
    public UserLike findByUserAndNoticeBoard(User user, NoticeBoard noticeBoard);
    public Integer countAllByNoticeBoard(NoticeBoard noticeBoard);
}

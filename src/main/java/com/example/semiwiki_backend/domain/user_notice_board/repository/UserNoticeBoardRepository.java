package com.example.semiwiki_backend.domain.user_notice_board.repository;

import com.example.semiwiki_backend.domain.user_notice_board.entity.UserNoticeBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserNoticeBoardRepository extends JpaRepository<UserNoticeBoard,Integer> {
    public List<UserNoticeBoard> findAllByUser_Id(int userId);
}

package com.example.semiwiki_backend.domain.notice_board.repository;

import com.example.semiwiki_backend.domain.notice_board.entity.NoticeBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NoticeBoardRepository extends JpaRepository<NoticeBoard, Integer> {
    @Query("SELECT DISTINCT n FROM NoticeBoard n JOIN n.categories c WHERE c IN :categories")
    public List<NoticeBoard> findAllByCategory(@Param("categories") List<String> categories);

    public List<NoticeBoard> findByTitleContaining(String keyword);
}

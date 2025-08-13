package com.example.semiwiki_backend.domain.notice_board.repository;

import com.example.semiwiki_backend.domain.notice_board.entity.NoticeBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NoticeBoardRepository extends JpaRepository<NoticeBoard, Integer> {
    @Query("SELECT n FROM NoticeBoard n JOIN n.categories c WHERE c IN :categories GROUP BY n HAVING COUNT(c) = SIZE(n.categories) ")
    List<NoticeBoard> findByCategoriesAllMatch(@Param("categories") List<String> categories, Pageable pageable);


    List<NoticeBoard> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    @Query(" SELECT n FROM NoticeBoard n JOIN n.categories c WHERE LOWER(n.title) LIKE LOWER(CONCAT('%', :title, '%')) AND c IN :categories GROUP BY n HAVING COUNT(c) = SIZE(n.categories) ")
    List<NoticeBoard> findAllByTitleContainingAndCategories(String title, List<String> categories, Pageable pageable);

    @Query("SELECT n FROM NoticeBoard n")
    List<NoticeBoard> findAllNoticeBoards(Pageable pageable);
}

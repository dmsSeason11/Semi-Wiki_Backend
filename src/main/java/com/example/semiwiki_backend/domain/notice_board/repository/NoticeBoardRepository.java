package com.example.semiwiki_backend.domain.notice_board.repository;

import com.example.semiwiki_backend.domain.notice_board.entity.NoticeBoard;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NoticeBoardRepository extends JpaRepository<NoticeBoard, Integer> {
    // 요청한 카테고리 전부를 포함한 게시글 조회
    @Query("""
        SELECT n 
        FROM NoticeBoard n 
        JOIN n.categories c 
        WHERE c IN :categories
        GROUP BY n
        HAVING COUNT(DISTINCT c) = :categoryCount
        """)
    List<NoticeBoard> findByCategoriesAllMatch(
            @Param("categories") List<String> categories,
            @Param("categoryCount") long categoryCount,
            Pageable pageable
    );

    List<NoticeBoard> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    @Query("""
        SELECT n 
        FROM NoticeBoard n 
        JOIN n.categories c 
        WHERE LOWER(n.title) LIKE LOWER(CONCAT('%', :title, '%'))
        AND c IN :categories
        GROUP BY n
        HAVING COUNT(DISTINCT c) = :categoryCount
        """)
    List<NoticeBoard> findByTitleContainingAndCategoriesAllMatch(
            @Param("title") String title,
            @Param("categories") List<String> categories,
            @Param("categoryCount") long categoryCount,
            Pageable pageable
    );

    // 최신순 정렬
    @Query("SELECT n FROM NoticeBoard n ORDER BY n.createdAt DESC")
    List<NoticeBoard> findAllNoticeBoards(Pageable pageable);
}

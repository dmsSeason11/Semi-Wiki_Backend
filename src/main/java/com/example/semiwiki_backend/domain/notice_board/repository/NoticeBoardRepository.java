package com.example.semiwiki_backend.domain.notice_board.repository;

import com.example.semiwiki_backend.domain.notice_board.entity.NoticeBoard;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NoticeBoardRepository extends JpaRepository<NoticeBoard, Integer> {

    boolean existsByTitle(String title);
    // 카테고리로 조회
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

    //제목 조회
    List<NoticeBoard> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    //카테고리, 제목 조회
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

    // 그냥 정렬
    @Query("SELECT n FROM NoticeBoard n ORDER BY n.createdAt DESC")
    List<NoticeBoard> findAllNoticeBoards(Pageable pageable);

    //여기는 좋아요 순으로 정렬

    //전체 좋아요순으로 정렬
    @Query("""
        SELECT n
        FROM NoticeBoard n
        LEFT JOIN UserLike ul ON ul.noticeBoard = n
        GROUP BY n
        ORDER BY COUNT(ul) DESC, n.createdAt DESC 
""")
    List<NoticeBoard> findAllOrderByLikeCountDescThenCreatedAtDesc(Pageable pageable);

    //카테고리로 조회
    @Query("""
        SELECT n
        FROM NoticeBoard n
        JOIN n.categories c
        LEFT JOIN UserLike ul ON ul.noticeBoard = n
        WHERE c IN :categories
        GROUP BY n
        HAVING COUNT(DISTINCT c) = :categoryCount
        ORDER BY COUNT(ul) DESC, n.createdAt DESC
""")
    List<NoticeBoard> findByCategoriesAllMatchOrderByLikeCountDesc(@Param("categories") List<String> categories, @Param("categoryCount") long categoryCount, Pageable pageable);

    //제목으로 조회
    @Query("""
        SELECT n
        FROM NoticeBoard n
        LEFT JOIN UserLike ul ON ul.noticeBoard = n
        WHERE lower(n.title) LIKE LOWER(CONCAT('%', :title, '%'))
        GROUP BY n
        ORDER BY count(ul) DESC, n.createdAt DESC
""")
    List<NoticeBoard> findByTitleContainingIgnoreCaseOrderByLikeCountDesc(@Param("title") String title, Pageable pageable);

    //카테고리,제목 둘다 조회
    @Query("""
        SELECT n
        FROM NoticeBoard n
        JOIN n.categories c
        LEFT JOIN UserLike ul ON ul.noticeBoard = n
        WHERE lower(n.title) LIKE LOWER(CONCAT('%',:title,'%'))
            AND c IN :categories
        GROUP BY n
        HAVING COUNT(DISTINCT c) = :categoryCount
        ORDER BY COUNT(ul) DESC, n.createdAt DESC
""")
    List<NoticeBoard> findByTitleContainingIgnoreCaseAndCategoriesOrderByLikeCountDesc(@Param("title") String title, @Param("categories") List<String> categories, @Param("categoryCount") long categoryCount, Pageable pageable);

    //여기는 개수 세는 함수

    //제목 조회
    Long countByTitleContainingIgnoreCase(String title);

    //카테고리,제목 조회
    @Query("""
        SELECT COUNT(DISTINCT n)
        FROM NoticeBoard n
        WHERE n.id IN (
            SELECT n2.id
            FROM NoticeBoard n2
            JOIN n2.categories c
            WHERE LOWER(n2.title) LIKE LOWER(CONCAT('%', :title, '%'))
              AND c IN :categories
            GROUP BY n2.id
            HAVING COUNT(DISTINCT c) = :categoryCount
        )
""")
    Long countByTitleContainingAndCategoriesAllMatch(
            @Param("title") String title,
            @Param("categories") List<String> categories,
            @Param("categoryCount") long categoryCount
    );

    //카테고리 조회
    @Query("""
        SELECT COUNT(n)
        FROM NoticeBoard n
        WHERE n.id IN (
            SELECT n2.id
            FROM NoticeBoard n2
            JOIN n2.categories c
            WHERE c IN :categories
            GROUP BY n2.id
            HAVING COUNT(DISTINCT c) = :categoryCount
        )
""")
    Long countByCategoriesAllMatch(
            @Param("categories") List<String> categories,
            @Param("categoryCount") long categoryCount
    );
}

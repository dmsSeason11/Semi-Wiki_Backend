package com.example.semiwiki_backend.domain.user_notice_board.repository;

import com.example.semiwiki_backend.domain.notice_board.entity.NoticeBoard;
import com.example.semiwiki_backend.domain.user.entity.User;
import com.example.semiwiki_backend.domain.user_notice_board.entity.UserNoticeBoard;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserNoticeBoardRepository extends JpaRepository<UserNoticeBoard,Integer> {
    int countUserNoticeBoardsByUser(User user);

    boolean existsUserNoticeBoardByUserAndNoticeBoard(User user, NoticeBoard noticeBoard);
    //최신순 정렬

    //카테고리로 조회
    @Query("""
    SELECT n 
    FROM UserNoticeBoard unb
    JOIN unb.noticeBoard n
    WHERE unb.user.accountId = :accountId
    AND n.id IN (
        SELECT nb.id 
        FROM NoticeBoard nb
        JOIN nb.categories c
        WHERE c IN :categories
        GROUP BY nb.id
        HAVING COUNT(DISTINCT c) = :categoryCount
    )
    ORDER BY n.createdAt DESC
""")
    List<NoticeBoard> findByUserAndCategoriesAllMatch(
            @Param("accountId") String accountId,
            @Param("categories") List<String> categories,
            @Param("categoryCount") long categoryCount,
            Pageable pageable
    );

    // 제목으로 조회
    @Query("""
    SELECT n
    FROM UserNoticeBoard unb
    JOIN unb.noticeBoard n
    WHERE unb.user.accountId = :accountId
    AND LOWER(n.title) LIKE LOWER(CONCAT('%', :title, '%'))
    ORDER BY n.createdAt DESC
""")
    List<NoticeBoard> findByUserAndTitleContainingIgnoreCase(
            @Param("accountId") String accountId,
            @Param("title") String title,
            Pageable pageable
    );

    // 카테고리,제목 조회
    @Query("""
    SELECT n
    FROM UserNoticeBoard unb
    JOIN unb.noticeBoard n
    WHERE unb.user.accountId = :accountId
    AND LOWER(n.title) LIKE LOWER(CONCAT('%', :title, '%'))
    AND n.id IN (
        SELECT nb.id 
        FROM NoticeBoard nb
        JOIN nb.categories c
        WHERE c IN :categories
        GROUP BY nb.id
        HAVING COUNT(DISTINCT c) = :categoryCount
    )
    ORDER BY n.createdAt DESC
""")
    List<NoticeBoard> findByUserAndTitleContainingAndCategoriesAllMatch(
            @Param("accountId") String accountId,
            @Param("title") String title,
            @Param("categories") List<String> categories,
            @Param("categoryCount") long categoryCount,
            Pageable pageable
    );

    // 전체 조회
    @Query("""
    SELECT n
    FROM UserNoticeBoard unb
    JOIN unb.noticeBoard n
    WHERE unb.user.accountId = :accountId
    ORDER BY n.createdAt DESC
""")
    List<NoticeBoard> findAllByUserOrderByCreatedAtDesc(
            @Param("accountId") String accountId,
            Pageable pageable
    );

    //여기는 좋아요순

    // 전체 조회
    @Query("""
    SELECT n
    FROM UserNoticeBoard unb
    JOIN unb.noticeBoard n
    LEFT JOIN UserLike ul ON ul.noticeBoard.id = n.id
    WHERE unb.user.accountId = :accountId
    GROUP BY n.id
    ORDER BY COUNT(ul) DESC, n.createdAt DESC
""")
    List<NoticeBoard> findAllByUserOrderByLikeCountDescThenCreatedAtDesc(
            @Param("accountId") String accountId,
            Pageable pageable
    );

    // 카테고리 조회
    @Query("""
    SELECT n
    FROM UserNoticeBoard unb
    JOIN unb.noticeBoard n
    LEFT JOIN UserLike ul ON ul.noticeBoard.id = n.id
    WHERE unb.user.accountId = :accountId
    AND n.id IN (
        SELECT nb.id 
        FROM NoticeBoard nb
        JOIN nb.categories c
        WHERE c IN :categories
        GROUP BY nb.id
        HAVING COUNT(DISTINCT c) = :categoryCount
    )
    GROUP BY n.id
    ORDER BY COUNT(ul) DESC, n.createdAt DESC
""")
    List<NoticeBoard> findByUserAndCategoriesAllMatchOrderByLikeCountDesc(
            @Param("accountId") String accountId,
            @Param("categories") List<String> categories,
            @Param("categoryCount") long categoryCount,
            Pageable pageable
    );

    // 제목으로 조회
    @Query("""
    SELECT n
    FROM UserNoticeBoard unb
    JOIN unb.noticeBoard n
    LEFT JOIN UserLike ul ON ul.noticeBoard.id = n.id
    WHERE unb.user.accountId = :accountId
    AND LOWER(n.title) LIKE LOWER(CONCAT('%', :title, '%'))
    GROUP BY n.id
    ORDER BY COUNT(ul) DESC, n.createdAt DESC
""")
    List<NoticeBoard> findByUserAndTitleContainingIgnoreCaseOrderByLikeCountDesc(
            @Param("accountId") String accountId,
            @Param("title") String title,
            Pageable pageable
    );

    // 제목,카테고리로 조회
    @Query("""
    SELECT n
    FROM UserNoticeBoard unb
    JOIN unb.noticeBoard n
    LEFT JOIN UserLike ul ON ul.noticeBoard.id = n.id
    WHERE unb.user.accountId = :accountId
    AND LOWER(n.title) LIKE LOWER(CONCAT('%', :title, '%'))
    AND n.id IN (
        SELECT nb.id 
        FROM NoticeBoard nb
        JOIN nb.categories c
        WHERE c IN :categories
        GROUP BY nb.id
        HAVING COUNT(DISTINCT c) = :categoryCount
    )
    GROUP BY n.id
    ORDER BY COUNT(ul) DESC, n.createdAt DESC
""")
    List<NoticeBoard> findByUserAndTitleContainingAndCategoriesOrderByLikeCountDesc(
            @Param("accountId") String accountId,
            @Param("title") String title,
            @Param("categories") List<String> categories,
            @Param("categoryCount") long categoryCount,
            Pageable pageable
    );

    //개수 세는 함수

    // 카테고리로 개수
    @Query("""
SELECT COUNT(DISTINCT n.id) 
FROM UserNoticeBoard unb
JOIN unb.noticeBoard n
JOIN n.categories c
WHERE unb.user.accountId = :accountId
  AND c IN :categories
  AND n.id IN (
    SELECT n2.id
    FROM NoticeBoard n2
    JOIN n2.categories c2
    WHERE c2 IN :categories
    GROUP BY n2.id
    HAVING COUNT(DISTINCT c2) = :categoryCount
  )
""")
    Long countByUserAndCategoriesAllMatch(
            @Param("accountId") String accountId,
            @Param("categories") List<String> categories,
            @Param("categoryCount") long categoryCount
    );

    // 제목으로 조회
    @Query("""
SELECT COUNT(DISTINCT n)
FROM UserNoticeBoard unb
JOIN unb.noticeBoard n
WHERE unb.user.accountId = :accountId
  AND LOWER(n.title) LIKE LOWER(CONCAT('%', :title, '%'))
""")
    Long countByUserAndTitleContainingIgnoreCase(
            @Param("accountId") String accountId,
            @Param("title") String title
    );

    // 제목,카테고리 조회
    @Query("""
SELECT COUNT(DISTINCT n.id) 
FROM UserNoticeBoard unb
JOIN unb.noticeBoard n
JOIN n.categories c
WHERE unb.user.accountId = :accountId
  AND LOWER(n.title) LIKE LOWER(CONCAT('%', :title, '%'))
  AND n.id IN (
    SELECT n2.id
    FROM NoticeBoard n2
    JOIN n2.categories c2
    WHERE c2 IN :categories
    GROUP BY n2.id
    HAVING COUNT(DISTINCT c2) = :categoryCount
  )
""")
    Long countByUserAndTitleContainingAndCategoriesAllMatch(
            @Param("accountId") String accountId,
            @Param("title") String title,
            @Param("categories") List<String> categories,
            @Param("categoryCount") long categoryCount
    );

    // 전체 조회
    @Query("""
SELECT COUNT(DISTINCT n)
FROM UserNoticeBoard unb
JOIN unb.noticeBoard n
WHERE unb.user.accountId = :accountId
""")
    Long countAllByUser(
            @Param("accountId") String accountId
    );
}


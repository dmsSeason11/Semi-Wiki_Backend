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

    // 1. 카테고리 모두 일치 + 특정 유저
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

    // 2. 제목 검색 + 특정 유저
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

    // 3. 제목 + 카테고리 모두 일치 + 특정 유저
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

    // 4. 최신순 정렬 + 특정 유저
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

    // 5. 좋아요 수 내림차순 + 특정 유저 (JPQL)
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

    // 6. 카테고리 모두 일치 + 좋아요 수 내림차순 + 특정 유저
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

    // 7. 제목 검색 + 좋아요 수 내림차순 + 특정 유저
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

    // 8. 제목 + 카테고리 모두 일치 + 좋아요 수 내림차순 + 특정 유저
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

    //여기부터 count(건들지 마)

    // 또는 더 간단한 방법:
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

    // 2. 제목 검색 + 특정 유저 (이건 맞음)
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

    // 3. 제목 + 카테고리 모두 일치 + 특정 유저 (수정 필요)
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

    // 4. 특정 유저의 전체 글 개수 (이건 맞음)
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


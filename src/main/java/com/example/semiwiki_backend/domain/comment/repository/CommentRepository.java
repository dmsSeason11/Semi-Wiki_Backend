package com.example.semiwiki_backend.domain.comment.repository;

import com.example.semiwiki_backend.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByNoticeBoardId(int boardId);
}

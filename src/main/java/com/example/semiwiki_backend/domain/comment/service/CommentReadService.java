package com.example.semiwiki_backend.domain.comment.service;

import com.example.semiwiki_backend.domain.comment.dto.response.CommentDetailResponseDto;
import com.example.semiwiki_backend.domain.comment.entity.Comment;
import com.example.semiwiki_backend.domain.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentReadService {
    private final CommentRepository commentRepository;

    public List<CommentDetailResponseDto> loadAllCommentsByBoardId(int boardId) {
        List<Comment> comments = commentRepository.findByNoticeBoardId(boardId);

        List<CommentDetailResponseDto> commentDetailResponseDtos = new ArrayList<>();
        for (Comment comment : comments) {
            commentDetailResponseDtos.add(CommentDetailResponseDto.builder()
                            .accountId(comment.getUser().getAccountId())
                            .contents(comment.getContents())
                            .modificatedAt(comment.getModificatedAt())
                            .wroteAt(comment.getWroteAt())
                            .build());
        }

        return commentDetailResponseDtos;
    }
}

package com.example.semiwiki_backend.domain.comment.controller;

import com.example.semiwiki_backend.domain.comment.dto.request.CommentCreateRequestDto;
import com.example.semiwiki_backend.domain.comment.dto.request.CommentUpdateRequestDto;
import com.example.semiwiki_backend.domain.comment.dto.response.CommentDetailResponseDto;
import com.example.semiwiki_backend.domain.comment.service.CommentCreateService;
import com.example.semiwiki_backend.domain.comment.service.CommentDeleteService;
import com.example.semiwiki_backend.domain.comment.service.CommentUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentCreateService commentCreateService;
    private final CommentDeleteService commentDeleteService;
    private final CommentUpdateService commentUpdateService;

    @PostMapping("/{boardId}")
    public ResponseEntity<CommentDetailResponseDto> createCommentOnBoard(@PathVariable Integer boardId, @RequestBody CommentCreateRequestDto dto, Authentication authentication) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentCreateService.createComment(dto, authentication, boardId));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDetailResponseDto> updateComment(@PathVariable Integer commentId, @RequestBody CommentUpdateRequestDto dto, Authentication authentication) {
        return ResponseEntity.ok().body(commentUpdateService.updateComment(dto, authentication, commentId));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Integer commentId, Authentication authentication) {
        commentDeleteService.deleteComment(authentication, commentId);
        return ResponseEntity.ok().build();
    }
}

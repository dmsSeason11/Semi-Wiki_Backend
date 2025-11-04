package com.example.semiwiki_backend.domain.comment.service;

import com.example.semiwiki_backend.domain.auth.exception.NotAccountOwnerException;
import com.example.semiwiki_backend.domain.comment.entity.Comment;
import com.example.semiwiki_backend.domain.comment.exception.CommentNotFoundException;
import com.example.semiwiki_backend.domain.comment.repository.CommentRepository;
import com.example.semiwiki_backend.domain.user.repository.UserRepository;
import com.example.semiwiki_backend.global.security.auth.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentDeleteService {
    private final CommentRepository commentRepository;

    public void deleteComment(Authentication authentication, Integer commentId) {
        CustomUserDetails customUserDetails = (CustomUserDetails)authentication.getPrincipal();
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);

        if(comment.getUser().getId() != customUserDetails.getId())
            throw new NotAccountOwnerException();

        commentRepository.delete(comment);
    }
}

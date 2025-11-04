package com.example.semiwiki_backend.domain.comment.service;

import com.example.semiwiki_backend.domain.auth.exception.NotAccountOwnerException;
import com.example.semiwiki_backend.domain.comment.dto.request.CommentUpdateRequestDto;
import com.example.semiwiki_backend.domain.comment.dto.response.CommentDetailResponseDto;
import com.example.semiwiki_backend.domain.comment.entity.Comment;
import com.example.semiwiki_backend.domain.comment.exception.CommentNotFoundException;
import com.example.semiwiki_backend.domain.comment.repository.CommentRepository;
import com.example.semiwiki_backend.domain.user.entity.User;
import com.example.semiwiki_backend.domain.user.exception.UserNotFoundException;
import com.example.semiwiki_backend.domain.user.repository.UserRepository;
import com.example.semiwiki_backend.global.security.auth.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentUpdateService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final static Logger logger = LoggerFactory.getLogger(CommentUpdateService.class);

    @Transactional
    public CommentDetailResponseDto updateComment(CommentUpdateRequestDto commentUpdateRequestDto, Authentication authentication, Integer commentId) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userRepository.findById(customUserDetails.getId()).orElseThrow(UserNotFoundException::new);

        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);

        if(comment.getUser().getId() != customUserDetails.getId())
            throw new NotAccountOwnerException();

        comment.updateContents(commentUpdateRequestDto.getContents());

        commentRepository.save(comment);

        logger.info("user: {}\ncomment: {}\n", user.getAccountId(), comment.getContents());
        return CommentDetailResponseDto.builder()
                .wroteAt(comment.getWroteAt())
                .modificatedAt(comment.getModificatedAt())
                .contents(comment.getContents())
                .accountId(comment.getUser().getAccountId())
                .build();
    }
}

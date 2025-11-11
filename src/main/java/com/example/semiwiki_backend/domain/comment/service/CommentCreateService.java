package com.example.semiwiki_backend.domain.comment.service;

import com.example.semiwiki_backend.domain.comment.dto.request.CommentCreateRequestDto;
import com.example.semiwiki_backend.domain.comment.dto.response.CommentDetailResponseDto;
import com.example.semiwiki_backend.domain.comment.entity.Comment;
import com.example.semiwiki_backend.domain.comment.exception.CommentNotValidException;
import com.example.semiwiki_backend.domain.comment.repository.CommentRepository;
import com.example.semiwiki_backend.domain.notice_board.entity.NoticeBoard;
import com.example.semiwiki_backend.domain.notice_board.exception.NoticeBoardNotFoundException;
import com.example.semiwiki_backend.domain.notice_board.repository.NoticeBoardRepository;
import com.example.semiwiki_backend.domain.user.entity.User;
import com.example.semiwiki_backend.domain.user.exception.UserNotFoundException;
import com.example.semiwiki_backend.domain.user.repository.UserRepository;
import com.example.semiwiki_backend.global.security.auth.CustomUserDetails;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentCreateService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final NoticeBoardRepository noticeBoardRepository;
    private final static Logger logger = LoggerFactory.getLogger(CommentCreateService.class);

    @Transactional
    public CommentDetailResponseDto createComment(CommentCreateRequestDto commentCreateRequestDto, Authentication authentication, Integer noticeBoardId) {
        CustomUserDetails customUserDetails = (CustomUserDetails)authentication.getPrincipal();
        User user = userRepository.findById(customUserDetails.getId()).orElseThrow(UserNotFoundException::new);
        NoticeBoard noticeBoard = noticeBoardRepository.findById(noticeBoardId).orElseThrow(NoticeBoardNotFoundException::new);

        Comment comment = Comment.builder()
                        .contents(commentCreateRequestDto.getContents())
                        .user(user)
                        .noticeBoard(noticeBoard)
                        .build();

        if(!comment.isValidComment())
            throw new CommentNotValidException();

        comment = commentRepository.save(comment);
        noticeBoard.addComment(comment);

        logger.info("user: {}\ncomment: {}\n", user.getAccountId(), comment.getContents());

        return CommentDetailResponseDto.builder()
                .wroteAt(comment.getWroteAt())
                .modificatedAt(comment.getModificatedAt())
                .accountId(user.getAccountId())
                .contents(comment.getContents())
                .build();
    }
}

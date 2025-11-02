package com.example.semiwiki_backend.domain.user_like.service;

import com.example.semiwiki_backend.domain.user_like.entity.UserLike;
import com.example.semiwiki_backend.domain.user_like.exception.AlreadyLikedException;
import com.example.semiwiki_backend.domain.user_like.repository.UserLikeRepository;
import com.example.semiwiki_backend.domain.notice_board.exception.NoticeBoardNotFoundException;
import com.example.semiwiki_backend.domain.notice_board.repository.NoticeBoardRepository;
import com.example.semiwiki_backend.domain.user.exception.UserNotFoundException;
import com.example.semiwiki_backend.domain.user.repository.UserRepository;
import com.example.semiwiki_backend.global.security.auth.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserLikeCreateService {
    private final UserLikeRepository userLikeRepository;
    private final UserRepository userRepository;
    private final NoticeBoardRepository noticeBoardRepository;


    @Transactional
    public UserLike createLike(Authentication authentication, Integer boardId) {
        //유저 아이디 jwt토큰에서 가져옴
        CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
        Integer userId = userDetails.getId();

        UserLike userLike = userLikeRepository.findByUserAndNoticeBoard(userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException()), noticeBoardRepository.findById(boardId).orElseThrow(() -> new NoticeBoardNotFoundException()));

        if(userLike != null)
            throw new AlreadyLikedException();

        return userLikeRepository.save(UserLike.builder()
                .user(userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException()))
                .noticeBoard(noticeBoardRepository.findById(boardId).orElseThrow(() -> new NoticeBoardNotFoundException()))
                .build());
    }
}

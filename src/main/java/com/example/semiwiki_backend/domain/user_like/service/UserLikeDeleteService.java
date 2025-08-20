package com.example.semiwiki_backend.domain.user_like.service;

import com.example.semiwiki_backend.domain.user_like.entity.UserLike;
import com.example.semiwiki_backend.domain.user_like.exception.NotLikedException;
import com.example.semiwiki_backend.domain.user_like.repository.UserLikeRepository;
import com.example.semiwiki_backend.domain.notice_board.entity.NoticeBoard;
import com.example.semiwiki_backend.domain.notice_board.exception.NoticeBoardNotFoundException;
import com.example.semiwiki_backend.domain.notice_board.repository.NoticeBoardRepository;
import com.example.semiwiki_backend.domain.user.entity.User;
import com.example.semiwiki_backend.domain.user.exception.UserNotFoundException;
import com.example.semiwiki_backend.domain.user.repository.UserRepository;
import com.example.semiwiki_backend.global.security.auth.CustomUserDetails;
import com.example.semiwiki_backend.global.security.exception.JwtExpiredException;
import com.example.semiwiki_backend.global.security.exception.JwtInvalidException;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserLikeDeleteService {
    private final UserLikeRepository userLikeRepository;
    private final NoticeBoardRepository noticeBoardRepository;
    private final UserRepository userRepository;

    public void deleteLike(Authentication authentication, Integer boardId) {
        //유저 아이디 jwt토큰에서 가져옴
        CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
        Integer userId = userDetails.getId();

        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());
        NoticeBoard noticeBoard = noticeBoardRepository.findById(boardId).orElseThrow(() -> new NoticeBoardNotFoundException());
        UserLike userLike = userLikeRepository.findByUserAndNoticeBoard(user, noticeBoard);
        if(userLike == null)
            throw new NotLikedException();
        userLikeRepository.delete(userLike);
    }
}

package com.example.semiwiki_backend.domain.user_like.service;

import com.example.semiwiki_backend.domain.user_like.entity.UserLike;
import com.example.semiwiki_backend.domain.user_like.repository.UserLikeRepository;
import com.example.semiwiki_backend.domain.notice_board.entity.NoticeBoard;
import com.example.semiwiki_backend.domain.notice_board.exception.NoticeBoardNotFoundException;
import com.example.semiwiki_backend.domain.notice_board.repository.NoticeBoardRepository;
import com.example.semiwiki_backend.domain.user.entity.User;
import com.example.semiwiki_backend.domain.user.exception.UserNotFoundException;
import com.example.semiwiki_backend.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserLikeReadSerivce {
    private final UserLikeRepository userLikeRepository;
    private final NoticeBoardRepository noticeBoardRepository;
    private final UserRepository userRepository;

    public UserLikeReadSerivce(UserLikeRepository userLikeRepository, NoticeBoardRepository noticeBoardRepository, UserRepository userRepository) {
        this.userLikeRepository = userLikeRepository;
        this.noticeBoardRepository = noticeBoardRepository;
        this.userRepository = userRepository;
    }

    public Boolean isLike(Integer userId, Integer noticeBoardId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());
        NoticeBoard noticeBoard = noticeBoardRepository.findById(noticeBoardId).orElseThrow(() -> new NoticeBoardNotFoundException());
        UserLike userLike = userLikeRepository.findByUserAndNoticeBoard(user, noticeBoard);
        return userLike != null;
    }

    public Integer countAllLikes(Integer boardId) {
        NoticeBoard noticeBoard = noticeBoardRepository.findById(boardId).orElseThrow(() -> new NoticeBoardNotFoundException());
        return userLikeRepository.countAllByNoticeBoard(noticeBoard);
    }
}

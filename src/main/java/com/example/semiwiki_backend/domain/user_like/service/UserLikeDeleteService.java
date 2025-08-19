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
import org.springframework.stereotype.Service;

@Service
public class UserLikeDeleteService {
    private final UserLikeRepository userLikeRepository;
    private final NoticeBoardRepository noticeBoardRepository;
    private final UserRepository userRepository;

    public UserLikeDeleteService(UserLikeRepository userLikeRepository, NoticeBoardRepository noticeBoardRepository, UserRepository userRepository) {
        this.userLikeRepository = userLikeRepository;
        this.noticeBoardRepository = noticeBoardRepository;
        this.userRepository = userRepository;
    }

    public void deleteLike(Integer userId, Integer boardId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());
        NoticeBoard noticeBoard = noticeBoardRepository.findById(boardId).orElseThrow(() -> new NoticeBoardNotFoundException());
        UserLike userLike = userLikeRepository.findByUserAndNoticeBoard(user, noticeBoard);
        if(userLike == null)
            throw new NotLikedException();
        userLikeRepository.delete(userLike);
    }
}

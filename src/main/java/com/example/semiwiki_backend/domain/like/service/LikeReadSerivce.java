package com.example.semiwiki_backend.domain.like.service;

import com.example.semiwiki_backend.domain.like.entity.Like;
import com.example.semiwiki_backend.domain.like.repository.LikeRepository;
import com.example.semiwiki_backend.domain.notice_board.entity.NoticeBoard;
import com.example.semiwiki_backend.domain.notice_board.exception.NoticeBoardNotFoundException;
import com.example.semiwiki_backend.domain.notice_board.repository.NoticeBoardRepository;
import com.example.semiwiki_backend.domain.user.entity.User;
import com.example.semiwiki_backend.domain.user.exception.UserNotFoundException;
import com.example.semiwiki_backend.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class LikeReadSerivce {
    private final LikeRepository likeRepository;
    private final NoticeBoardRepository noticeBoardRepository;
    private final UserRepository userRepository;

    public LikeReadSerivce(LikeRepository likeRepository, NoticeBoardRepository noticeBoardRepository, UserRepository userRepository) {
        this.likeRepository = likeRepository;
        this.noticeBoardRepository = noticeBoardRepository;
        this.userRepository = userRepository;
    }

    public Boolean isLike(Integer userId, Integer noticeBoardId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("유저를 찾을 수 없습니다."));
        NoticeBoard noticeBoard = noticeBoardRepository.findById(noticeBoardId).orElseThrow(() -> new NoticeBoardNotFoundException("게시판을 찾을 수 없습니다."));
        Like like = likeRepository.findByUserAndNoticeBoard(user, noticeBoard);
        return like != null;
    }

    public Integer countAllLikes(Integer boardId) {
        NoticeBoard noticeBoard = noticeBoardRepository.findById(boardId).orElseThrow(() -> new NoticeBoardNotFoundException("게시판을 찾을수 없습니다."));
        return likeRepository.countAllByNoticeBoard(noticeBoard);
    }
}

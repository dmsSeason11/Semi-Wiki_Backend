package com.example.semiwiki_backend.domain.like.service;

import com.example.semiwiki_backend.domain.like.entity.Like;
import com.example.semiwiki_backend.domain.like.exception.NotLikedException;
import com.example.semiwiki_backend.domain.like.repository.LikeRepository;
import com.example.semiwiki_backend.domain.notice_board.entity.NoticeBoard;
import com.example.semiwiki_backend.domain.notice_board.exception.NoticeBoardNotFoundException;
import com.example.semiwiki_backend.domain.notice_board.repository.NoticeBoardRepository;
import com.example.semiwiki_backend.domain.user.entity.User;
import com.example.semiwiki_backend.domain.user.exception.UserNotFoundException;
import com.example.semiwiki_backend.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class LikeDeleteService {
    private final LikeRepository likeRepository;
    private final NoticeBoardRepository noticeBoardRepository;
    private final UserRepository userRepository;

    public LikeDeleteService(LikeRepository likeRepository, NoticeBoardRepository noticeBoardRepository, UserRepository userRepository) {
        this.likeRepository = likeRepository;
        this.noticeBoardRepository = noticeBoardRepository;
        this.userRepository = userRepository;
    }

    public void deleteLike(Integer userId, Integer boardId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("유저를 찾을 수 없습니다."));
        NoticeBoard noticeBoard = noticeBoardRepository.findById(boardId).orElseThrow(() -> new NoticeBoardNotFoundException("게시판을 찾을 수 없습니다."));
        Like like = likeRepository.findByUserAndNoticeBoard(user, noticeBoard);
        if(like == null)
            throw new NotLikedException("이미 좋아요를 해제한 상태입니다.");
        likeRepository.delete(like);
    }
}

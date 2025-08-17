package com.example.semiwiki_backend.domain.like.service;

import com.example.semiwiki_backend.domain.like.entity.Like;
import com.example.semiwiki_backend.domain.like.exception.AlreadyLikedException;
import com.example.semiwiki_backend.domain.like.repository.LikeRepository;
import com.example.semiwiki_backend.domain.notice_board.exception.NoticeBoardNotFoundException;
import com.example.semiwiki_backend.domain.notice_board.repository.NoticeBoardRepository;
import com.example.semiwiki_backend.domain.user.exception.UserNotFoundException;
import com.example.semiwiki_backend.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikeCreateService {
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final NoticeBoardRepository noticeBoardRepository;

    public LikeCreateService(LikeRepository likeRepository, UserRepository userRepository, NoticeBoardRepository noticeBoardRepository) {
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
        this.noticeBoardRepository = noticeBoardRepository;
    }

    @Transactional
    public Like createLike(Integer userId, Integer boardId) {

        Like like = likeRepository.findByUserAndNoticeBoard(userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("유저를 찾지 못했습니다.")), noticeBoardRepository.findById(boardId).orElseThrow(() -> new NoticeBoardNotFoundException("게시판을 찾을수 없습니다.")));

        if(like != null)
            throw new AlreadyLikedException("이미 좋아요를 눌렀습니다.");

        return likeRepository.save(Like.builder()
                .user(userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("유저를 찾지 못했습니다.")))
                .noticeBoard(noticeBoardRepository.findById(boardId).orElseThrow(() -> new NoticeBoardNotFoundException("게시판을 찾을수 없습니다.")))
                .build());
    }
}

package com.example.semiwiki_backend.domain.notice_board.service;

import com.example.semiwiki_backend.domain.notice_board.entity.NoticeBoard;
import com.example.semiwiki_backend.domain.notice_board.exception.NoticeBoardNotFoundException;
import com.example.semiwiki_backend.domain.notice_board.repository.NoticeBoardRepository;
import com.example.semiwiki_backend.domain.user_notice_board.repository.UserNoticeBoardRepository;
import org.springframework.stereotype.Service;

@Service
public class NoticeBoardDeleteService {
    private final UserNoticeBoardRepository userNoticeBoardRepository;
    private final NoticeBoardRepository noticeBoardRepository;

    public NoticeBoardDeleteService(UserNoticeBoardRepository userNoticeBoardRepository, NoticeBoardRepository noticeBoardRepository) {
        this.userNoticeBoardRepository = userNoticeBoardRepository;
        this.noticeBoardRepository = noticeBoardRepository;
    }

    public void deleteNotice(int noticeId) {
        NoticeBoard noticeBoard = noticeBoardRepository.findById(noticeId).orElseThrow(() -> new NoticeBoardNotFoundException("noticeId : " + noticeId + "게시글이 없습니다."));

        noticeBoardRepository.delete(noticeBoard);
    }
}

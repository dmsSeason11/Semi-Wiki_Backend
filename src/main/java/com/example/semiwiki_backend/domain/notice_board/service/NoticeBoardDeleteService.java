package com.example.semiwiki_backend.domain.notice_board.service;

import com.example.semiwiki_backend.domain.notice_board.entity.NoticeBoard;
import com.example.semiwiki_backend.domain.notice_board.exception.NoticeBoardNotFoundException;
import com.example.semiwiki_backend.domain.notice_board.repository.NoticeBoardRepository;
import com.example.semiwiki_backend.domain.user_notice_board.repository.UserNoticeBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoticeBoardDeleteService {
    private final UserNoticeBoardRepository userNoticeBoardRepository;
    private final NoticeBoardRepository noticeBoardRepository;


    public void deleteNotice(int noticeId) {
        NoticeBoard noticeBoard = noticeBoardRepository.findById(noticeId).orElseThrow(() -> new NoticeBoardNotFoundException());

        noticeBoardRepository.delete(noticeBoard);
    }
}

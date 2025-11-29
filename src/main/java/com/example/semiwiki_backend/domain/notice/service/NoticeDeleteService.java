package com.example.semiwiki_backend.domain.notice.service;

import com.example.semiwiki_backend.domain.notice.entity.Notice;
import com.example.semiwiki_backend.domain.notice.exception.NoticeNotFoundException;
import com.example.semiwiki_backend.domain.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoticeDeleteService {
    private final NoticeRepository noticeRepository;

    public void deleteNotice(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(() -> new NoticeNotFoundException());
        noticeRepository.delete(notice);
    }
}

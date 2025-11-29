package com.example.semiwiki_backend.domain.notice.service;

import com.example.semiwiki_backend.domain.notice.dto.request.NoticeUpdateRequestDto;
import com.example.semiwiki_backend.domain.notice.dto.response.NoticeResponseDto;
import com.example.semiwiki_backend.domain.notice.entity.Notice;
import com.example.semiwiki_backend.domain.notice.exception.NoticeNotFoundException;
import com.example.semiwiki_backend.domain.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NoticeUpdateService {
    private final NoticeRepository noticeRepository;

    @Transactional
    public NoticeResponseDto updateNotice(NoticeUpdateRequestDto dto, Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(NoticeNotFoundException::new);

        notice.updateNotice(dto.getTitle(), dto.getContents(), dto.getType());
        noticeRepository.save(notice);

        return NoticeResponseDto.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .contents(notice.getContents())
                .type(notice.getType())
                .build();
    }
}

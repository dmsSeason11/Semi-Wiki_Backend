package com.example.semiwiki_backend.domain.notice.service;

import com.example.semiwiki_backend.domain.notice.dto.request.NoticeCreateRequestDto;
import com.example.semiwiki_backend.domain.notice.dto.response.NoticeResponseDto;
import com.example.semiwiki_backend.domain.notice.entity.Notice;
import com.example.semiwiki_backend.domain.notice.repository.NoticeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoticeCreateService {
    private final NoticeRepository noticeRepository;

    @Transactional
    public NoticeResponseDto postNotice(NoticeCreateRequestDto dto){
        Notice notice = Notice.builder()
                .title(dto.getTitle())
                .contents(dto.getContents())
                .type(dto.getType())
                .build();

        notice = noticeRepository.save(notice);

        return NoticeResponseDto.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .contents(notice.getContents())
                .type(notice.getType())
                .build();
    }
}

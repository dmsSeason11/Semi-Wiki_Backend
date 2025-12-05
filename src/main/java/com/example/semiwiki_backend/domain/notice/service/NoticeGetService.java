package com.example.semiwiki_backend.domain.notice.service;

import com.example.semiwiki_backend.domain.notice.dto.response.NoticeResponseDto;
import com.example.semiwiki_backend.domain.notice.entity.Notice;
import com.example.semiwiki_backend.domain.notice.exception.NoticeNotFoundException;
import com.example.semiwiki_backend.domain.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeGetService {
    private final NoticeRepository noticeRepository;

    public NoticeResponseDto getNotice(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(NoticeNotFoundException::new);
        return NoticeResponseDto.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .contents(notice.getContents())
                .type(notice.getType())
                .build();
    }

    public List<NoticeResponseDto> getAllNotices() {
        List<Notice> noticeList = noticeRepository.findAll();
        List<NoticeResponseDto> noticeResponseDtoList = new ArrayList<>();
        for (Notice notice : noticeList) {
            noticeResponseDtoList.add(NoticeResponseDto.builder()
                    .id(notice.getId())
                    .title(notice.getTitle())
                    .contents(notice.getContents())
                    .type(notice.getType())
                    .build());
        }
        return noticeResponseDtoList;
    }
}

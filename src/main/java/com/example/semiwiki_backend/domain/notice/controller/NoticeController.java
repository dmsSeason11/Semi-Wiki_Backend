package com.example.semiwiki_backend.domain.notice.controller;

import com.example.semiwiki_backend.domain.notice.dto.request.NoticeCreateRequestDto;
import com.example.semiwiki_backend.domain.notice.dto.request.NoticeUpdateRequestDto;
import com.example.semiwiki_backend.domain.notice.dto.response.NoticeResponseDto;
import com.example.semiwiki_backend.domain.notice.service.NoticeCreateService;
import com.example.semiwiki_backend.domain.notice.service.NoticeDeleteService;
import com.example.semiwiki_backend.domain.notice.service.NoticeGetService;
import com.example.semiwiki_backend.domain.notice.service.NoticeUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {
    private final NoticeCreateService noticeCreateService;
    private final NoticeGetService noticeGetService;
    private final NoticeDeleteService noticeDeleteService;
    private final NoticeUpdateService noticeUpdateService;

    @PostMapping
    public ResponseEntity<NoticeResponseDto> postNotice(@RequestBody NoticeCreateRequestDto noticeCreateRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(noticeCreateService.postNotice(noticeCreateRequestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoticeResponseDto> getNotice(@PathVariable Long id) {
        return ResponseEntity.ok().body(noticeGetService.getNotice(id));
    }

    @GetMapping
    public ResponseEntity<List<NoticeResponseDto>> getNoticesAll() {
        return ResponseEntity.ok().body(noticeGetService.getAllNotices());
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoticeResponseDto> updateNotice(@RequestBody NoticeUpdateRequestDto noticeUpdateRequestDto, @PathVariable Long id) {
        return ResponseEntity.ok().body(noticeUpdateService.updateNotice(noticeUpdateRequestDto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNotice(@PathVariable Long id) {
        noticeDeleteService.deleteNotice(id);
        return ResponseEntity.noContent().build();
    }
}

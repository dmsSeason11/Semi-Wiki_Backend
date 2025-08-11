package com.example.semiwiki_backend.domain.notice_table.controller;

import com.example.semiwiki_backend.domain.notice_table.dto.request.NoticeTableCreateRequestDto;
import com.example.semiwiki_backend.domain.notice_table.entity.NoticeTable;
import com.example.semiwiki_backend.domain.notice_table.service.NoticeTableCreateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("notice-board")
public class NoticeTableController {
    private final NoticeTableCreateService noticeTableCreateService;

    public NoticeTableController(NoticeTableCreateService noticeTableCreateService) {
        this.noticeTableCreateService = noticeTableCreateService;
    }

    @PostMapping("/post")
    public ResponseEntity<NoticeTable> createNoticeBoard(NoticeTableCreateRequestDto dto){
        NoticeTable noticeTable = noticeTableCreateService.createNoticeBoard(dto);
        if(noticeTable == null)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(noticeTable);
    }
}

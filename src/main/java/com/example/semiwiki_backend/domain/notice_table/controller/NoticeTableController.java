package com.example.semiwiki_backend.domain.notice_table.controller;

import com.example.semiwiki_backend.domain.notice_table.dto.request.NoticeTableCreateRequestDto;
import com.example.semiwiki_backend.domain.notice_table.dto.request.NoticeTableUpdateRequest;
import com.example.semiwiki_backend.domain.notice_table.dto.response.NoticeTableDetailResponseDto;
import com.example.semiwiki_backend.domain.notice_table.dto.response.NoticeTableListResponseDto;
import com.example.semiwiki_backend.domain.notice_table.entity.NoticeTable;
import com.example.semiwiki_backend.domain.notice_table.service.NoticeTableCreateService;
import com.example.semiwiki_backend.domain.notice_table.service.NoticeTableGetService;
import com.example.semiwiki_backend.domain.notice_table.service.NoticeTableUpdateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("notice-board")
public class NoticeTableController {
    private final NoticeTableCreateService noticeTableCreateService;
    private final NoticeTableGetService noticeTableGetService;
    private final NoticeTableUpdateService noticeTableUpdateService;

    public NoticeTableController(NoticeTableCreateService noticeTableCreateService, NoticeTableGetService noticeTableGetService, NoticeTableUpdateService noticeTableUpdateService) {
        this.noticeTableCreateService = noticeTableCreateService;
        this.noticeTableGetService = noticeTableGetService;
        this.noticeTableUpdateService = noticeTableUpdateService;
    }

    @PostMapping("/post")
    public ResponseEntity<NoticeTable> createNoticeBoard(@RequestBody NoticeTableCreateRequestDto dto){
        NoticeTable noticeTable = noticeTableCreateService.createNoticeBoard(dto);
        if(noticeTable == null)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(noticeTable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoticeTableDetailResponseDto> getNoticeBoardById(@PathVariable("id") Integer id){
        return ResponseEntity.ok().body(noticeTableGetService.getNoticeTable(id));
    }

    @GetMapping("/list")
    public ResponseEntity<List<NoticeTableListResponseDto>> listNoticeBoard(@RequestParam(value = "categories", required = false) List<String> categories){
        if(categories == null || categories.isEmpty())
            return ResponseEntity.ok().body(noticeTableGetService.getAllNoticeTables());
        return ResponseEntity.ok().body(noticeTableGetService.getNoticeTableListByCategorys(categories));
    }

    @GetMapping("/search")
    public ResponseEntity<List<NoticeTableListResponseDto>> searchNoticeBoard(@RequestParam(value = "q", required = false) String q){
        if(q == null || q.isEmpty())
            return ResponseEntity.ok().body(noticeTableGetService.getAllNoticeTables());
        return ResponseEntity.ok().body(noticeTableGetService.searchNoticeTables(q));
    }

    @PutMapping("/put")
    public ResponseEntity<NoticeTableDetailResponseDto> updateNoticeBoard(@RequestBody NoticeTableUpdateRequest dto){
        return ResponseEntity.ok().body(noticeTableUpdateService.updateNoticeTable(dto));
    }
}

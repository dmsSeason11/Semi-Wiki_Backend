package com.example.semiwiki_backend.domain.notice_table.controller;

import com.example.semiwiki_backend.domain.notice_table.dto.request.NoticeTableCreateRequestDto;
import com.example.semiwiki_backend.domain.notice_table.dto.response.NoticeTableDetailResponseDto;
import com.example.semiwiki_backend.domain.notice_table.dto.response.NoticeTableListResponseDto;
import com.example.semiwiki_backend.domain.notice_table.entity.NoticeTable;
import com.example.semiwiki_backend.domain.notice_table.service.NoticeTableCreateService;
import com.example.semiwiki_backend.domain.notice_table.service.NoticeTableGetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("notice-board")
public class NoticeTableController {
    private final NoticeTableCreateService noticeTableCreateService;
    private final NoticeTableGetService noticeTableGetService;

    public NoticeTableController(NoticeTableCreateService noticeTableCreateService, NoticeTableGetService noticeTableGetService) {
        this.noticeTableCreateService = noticeTableCreateService;
        this.noticeTableGetService = noticeTableGetService;
    }

    @PostMapping("/post")
    public ResponseEntity<NoticeTable> createNoticeBoard(NoticeTableCreateRequestDto dto){
        NoticeTable noticeTable = noticeTableCreateService.createNoticeBoard(dto);
        if(noticeTable == null)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(noticeTable);
    }

    @GetMapping("/${id}")
    public ResponseEntity<NoticeTableDetailResponseDto> getNoticeBoardById(@PathVariable("id") Integer id){
        return ResponseEntity.ok().body(noticeTableGetService.getNoticeTable(id));
    }

    @GetMapping("/list")
    ResponseEntity<List<NoticeTableListResponseDto>> listNoticeBoard(@RequestParam(value = "categories", required = false) List<String> category){
        if(category == null || category.isEmpty())
            return ResponseEntity.ok().body(noticeTableGetService.getAllNoticeTables());
        return ResponseEntity.ok().body(noticeTableGetService.getNoticeTableListByCategorys(category));
    }
}

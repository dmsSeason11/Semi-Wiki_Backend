package com.example.semiwiki_backend.domain.notice_board.controller;

import com.example.semiwiki_backend.domain.notice_board.dto.request.NoticeBoardCreateRequestDto;
import com.example.semiwiki_backend.domain.notice_board.dto.request.NoticeBoardUpdateRequest;
import com.example.semiwiki_backend.domain.notice_board.dto.response.NoticeBoardDetailResponseDto;
import com.example.semiwiki_backend.domain.notice_board.dto.response.NoticeBoardListResponseDto;
import com.example.semiwiki_backend.domain.notice_board.entity.NoticeBoard;
import com.example.semiwiki_backend.domain.notice_board.service.NoticeBoardCreateService;
import com.example.semiwiki_backend.domain.notice_board.service.NoticeBoardDeleteService;
import com.example.semiwiki_backend.domain.notice_board.service.NoticeBoardGetService;
import com.example.semiwiki_backend.domain.notice_board.service.NoticeBoardUpdateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("notice-board")
public class NoticeBoardController {
    private final NoticeBoardCreateService noticeBoardCreateService;
    private final NoticeBoardGetService noticeBoardGetService;
    private final NoticeBoardUpdateService noticeBoardUpdateService;
    private final NoticeBoardDeleteService noticeBoardDeleteService;

    public NoticeBoardController(NoticeBoardCreateService noticeBoardCreateService, NoticeBoardGetService noticeBoardGetService, NoticeBoardUpdateService noticeBoardUpdateService, NoticeBoardDeleteService noticeBoardDeleteService) {
        this.noticeBoardCreateService = noticeBoardCreateService;
        this.noticeBoardGetService = noticeBoardGetService;
        this.noticeBoardUpdateService = noticeBoardUpdateService;
        this.noticeBoardDeleteService = noticeBoardDeleteService;
    }

    @PostMapping("/post")
    public ResponseEntity<NoticeBoard> createNoticeBoard(@RequestBody NoticeBoardCreateRequestDto dto){
        NoticeBoard noticeBoard = noticeBoardCreateService.createNoticeBoard(dto);
        if(noticeBoard == null)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(noticeBoard);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoticeBoardDetailResponseDto> getNoticeBoardById(@PathVariable("id") Integer id){
        return ResponseEntity.ok().body(noticeBoardGetService.getNoticeBoard(id));
    }

    @GetMapping("/list")
    public ResponseEntity<List<NoticeBoardListResponseDto>> listNoticeBoard(@RequestParam(value = "categories", required = false) List<String> categories){
        if(categories == null || categories.isEmpty())
            return ResponseEntity.ok().body(noticeBoardGetService.getAllNoticeBoards());
        return ResponseEntity.ok().body(noticeBoardGetService.getNoticeBoardListByCategorys(categories));
    }

    @GetMapping("/search")
    public ResponseEntity<List<NoticeBoardListResponseDto>> searchNoticeBoard(@RequestParam(value = "q", required = false) String q){
        if(q == null || q.isEmpty())
            return ResponseEntity.ok().body(noticeBoardGetService.getAllNoticeBoards());
        return ResponseEntity.ok().body(noticeBoardGetService.searchNoticeBoards(q));
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<NoticeBoardDetailResponseDto> updateNoticeBoard(@PathVariable Integer id, @RequestBody NoticeBoardUpdateRequest dto){
        return ResponseEntity.ok().body(noticeBoardUpdateService.updateNoticeBoard(dto,id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteNoticeBoard(@PathVariable Integer id){
        noticeBoardDeleteService.deleteNotice(id);
        return ResponseEntity.ok().build();
    }
}

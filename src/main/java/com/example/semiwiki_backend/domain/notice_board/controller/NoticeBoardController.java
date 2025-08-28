package com.example.semiwiki_backend.domain.notice_board.controller;

import com.example.semiwiki_backend.domain.notice_board.dto.request.NoticeBoardCreateRequestDto;
import com.example.semiwiki_backend.domain.notice_board.dto.request.NoticeBoardListDto;
import com.example.semiwiki_backend.domain.notice_board.dto.request.NoticeBoardHeaderUpdateRequestDto;
import com.example.semiwiki_backend.domain.notice_board.dto.response.NoticeBoardDetailResponseDto;
import com.example.semiwiki_backend.domain.notice_board.dto.response.NoticeBoardListResponseDto;
import com.example.semiwiki_backend.domain.notice_board.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("notice-board")
@RequiredArgsConstructor
public class NoticeBoardController {
    private final NoticeBoardCreateService noticeBoardCreateService;
    private final NoticeBoardGetDetailService noticeBoardGetDetailService;
    private final NoticeBoardUpdateService noticeBoardUpdateService;
    private final NoticeBoardDeleteService noticeBoardDeleteService;
    private final NoticeBoardGetListService noticeBoardGetListService;

    @PostMapping("/post")
    public ResponseEntity<NoticeBoardDetailResponseDto> createNoticeBoard(@RequestBody NoticeBoardCreateRequestDto dto, Authentication authentication) {
        NoticeBoardDetailResponseDto noticeBoardDetailResponseDto = noticeBoardCreateService.createNoticeBoard(dto,authentication);
        if(noticeBoardDetailResponseDto == null)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(noticeBoardDetailResponseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoticeBoardDetailResponseDto> getNoticeBoardById(@PathVariable("id") Integer id){
        return ResponseEntity.ok().body(noticeBoardGetDetailService.getNoticeBoard(id));
    }

    @GetMapping("/list")
    public ResponseEntity<List<NoticeBoardListResponseDto>> listNoticeBoard(@RequestBody NoticeBoardListDto noticeBoardListDto){
        return ResponseEntity.ok().body(noticeBoardGetListService.getNoticeBoardList(noticeBoardListDto));
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<NoticeBoardDetailResponseDto> updateNoticeBoard(@PathVariable Integer id, @RequestBody NoticeBoardHeaderUpdateRequestDto dto, Authentication authentication){
        return ResponseEntity.ok().body(noticeBoardUpdateService.updateNoticeBoard(dto,id,authentication));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteNoticeBoard(@PathVariable Integer id){
        noticeBoardDeleteService.deleteNotice(id);
        return ResponseEntity.ok().build();
    }
}

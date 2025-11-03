package com.example.semiwiki_backend.domain.notice_board.controller;

import com.example.semiwiki_backend.domain.notice_board.dto.request.NoticeBoardCreateRequestDto;

import com.example.semiwiki_backend.domain.notice_board.dto.request.NoticeBoardUpdateRequestDto;
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
    private final NoticeBoardGetCountService noticeBoardGetCountService;

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
    public ResponseEntity<List<NoticeBoardListResponseDto>> listNoticeBoard(@RequestParam(required = false) List<String> categories,
                                                                            @RequestParam(required = false) String keyword,
                                                                            @RequestParam(required = false, defaultValue = "0") int offset,
                                                                            @RequestParam(required = false, defaultValue = "20") int limit,
                                                                            @RequestParam(required = false, defaultValue = "recent") String orderBy){
        return ResponseEntity.ok().body(noticeBoardGetListService.getNoticeBoardList(categories,keyword,offset,limit,orderBy));
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<NoticeBoardDetailResponseDto> updateNoticeBoard(@PathVariable Integer id, @RequestBody NoticeBoardUpdateRequestDto dto, Authentication authentication){
        return ResponseEntity.ok().body(noticeBoardUpdateService.updateNoticeBoard(dto,id,authentication));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteNoticeBoard(@PathVariable Integer id){
        noticeBoardDeleteService.deleteNotice(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countNoticeBoard(@RequestParam(required = false) List<String> categories,
                                                 @RequestParam(required = false) String keyword){
        return ResponseEntity.ok().body(noticeBoardGetCountService.noticeBoardGetCount(keyword, categories));
    }
}

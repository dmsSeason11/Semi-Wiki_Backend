package com.example.semiwiki_backend.domain.notice_board.controller;

import com.example.semiwiki_backend.domain.notice_board.dto.request.NoticeBoardCreateRequestDto;
import com.example.semiwiki_backend.domain.notice_board.dto.request.NoticeBoardListDto;
import com.example.semiwiki_backend.domain.notice_board.dto.request.NoticeBoardUpdateRequestDto;
import com.example.semiwiki_backend.domain.notice_board.dto.response.NoticeBoardDetailResponseDto;
import com.example.semiwiki_backend.domain.notice_board.dto.response.NoticeBoardListResponseDto;
import com.example.semiwiki_backend.domain.notice_board.entity.NoticeBoard;
import com.example.semiwiki_backend.domain.notice_board.service.NoticeBoardCreateService;
import com.example.semiwiki_backend.domain.notice_board.service.NoticeBoardDeleteService;
import com.example.semiwiki_backend.domain.notice_board.service.NoticeBoardGetService;
import com.example.semiwiki_backend.domain.notice_board.service.NoticeBoardUpdateService;
import com.example.semiwiki_backend.global.security.auth.CustomUserDetails;
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
    private final NoticeBoardGetService noticeBoardGetService;
    private final NoticeBoardUpdateService noticeBoardUpdateService;
    private final NoticeBoardDeleteService noticeBoardDeleteService;

    @PostMapping("/post")
    public ResponseEntity<NoticeBoard> createNoticeBoard(@RequestBody NoticeBoardCreateRequestDto dto, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
        Integer userId = userDetails.getId();
        NoticeBoard noticeBoard = noticeBoardCreateService.createNoticeBoard(dto,userId);
        if(noticeBoard == null)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(noticeBoard);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoticeBoardDetailResponseDto> getNoticeBoardById(@PathVariable("id") Integer id){
        return ResponseEntity.ok().body(noticeBoardGetService.getNoticeBoard(id));
    }

    @GetMapping("/list")
    public ResponseEntity<List<NoticeBoardListResponseDto>> listNoticeBoard(@RequestBody NoticeBoardListDto noticeBoardListDto){
        //dto에서 값 빼냄
        final List<String> categories = noticeBoardListDto.getCategories();
        final String keyword = noticeBoardListDto.getKeyword();
        final int offset = noticeBoardListDto.getOffset();
        final int limit = noticeBoardListDto.getLimit();

        //카테고리가 없는경우
        if(categories == null || categories.isEmpty()) {
            //카테고리랑 키워드 둘다 없는경우
            if(keyword == null || keyword.isEmpty())
                return ResponseEntity.ok().body(noticeBoardGetService.getAllNoticeBoards(offset, limit));
            //카테고리만 없는경우 == 키워드는 있는경우
            return ResponseEntity.ok().body(noticeBoardGetService.searchNoticeBoards(keyword, offset, limit));
        }
        //키워드만 없는경우
        if(keyword == null || keyword.isEmpty())
            return ResponseEntity.ok().body(noticeBoardGetService.getNoticeBoardListByCategories(categories, offset, limit));
        //둘 다 있는 경우
        return ResponseEntity.ok().body(noticeBoardGetService.searchAndFindByCategoryNoticeBoards(keyword, categories, offset, limit));
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<NoticeBoardDetailResponseDto> updateNoticeBoard(@PathVariable Integer id, @RequestBody NoticeBoardUpdateRequestDto dto, Authentication authentication){
        CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
        Integer userId = userDetails.getId();
        return ResponseEntity.ok().body(noticeBoardUpdateService.updateNoticeBoard(dto,id,userId));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteNoticeBoard(@PathVariable Integer id){
        noticeBoardDeleteService.deleteNotice(id);
        return ResponseEntity.ok().build();
    }
}

package com.example.semiwiki_backend.domain.user.controller;

import com.example.semiwiki_backend.domain.notice_board.dto.request.NoticeBoardCountRequestDto;
import com.example.semiwiki_backend.domain.notice_board.dto.request.NoticeBoardListDto;
import com.example.semiwiki_backend.domain.notice_board.dto.response.NoticeBoardListResponseDto;
import com.example.semiwiki_backend.domain.user.dto.response.UserMyPageResponseDto;
import com.example.semiwiki_backend.domain.user.service.UserReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserReadService userReadService;

    @GetMapping("{accountId}")
    public ResponseEntity<UserMyPageResponseDto> getUserMypageInfo(@PathVariable String accountId) {
        return ResponseEntity.ok().body(userReadService.GetUserInfo(accountId));
    }

    @GetMapping("{accountId}/list")
    public ResponseEntity<List<NoticeBoardListResponseDto>> getUserNoticeBoardList(@PathVariable String accountId, @RequestBody NoticeBoardListDto noticeBoardListDto) {
        List<NoticeBoardListResponseDto> noticeBoardListResponseDtos = userReadService.GetNoticeBoardsFromUser(accountId, noticeBoardListDto);
        if(noticeBoardListResponseDtos == null || noticeBoardListResponseDtos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(noticeBoardListResponseDtos);
    }
    @GetMapping("{accountId}/count")
    public ResponseEntity<Long> getUserCount(@PathVariable String accountId, @RequestBody NoticeBoardCountRequestDto noticeBoardCountRequestDto) {
        return ResponseEntity.ok().body(userReadService.GetNoticeBoardCount(accountId, noticeBoardCountRequestDto));
    }
}

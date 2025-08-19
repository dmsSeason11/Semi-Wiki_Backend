package com.example.semiwiki_backend.domain.user.controller;

import com.example.semiwiki_backend.domain.notice_board.dto.response.NoticeBoardListResponseDto;
import com.example.semiwiki_backend.domain.user.dto.response.UserMyPageResponseDto;
import com.example.semiwiki_backend.domain.user.service.UserReadService;
import com.example.semiwiki_backend.domain.user_notice_board.entity.UserNoticeBoard;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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
    public ResponseEntity<List<NoticeBoardListResponseDto>> getUserNoticeBoardList(@PathVariable String accountId) {
        List<NoticeBoardListResponseDto> noticeBoardListResponseDtos = userReadService.GetNoticeBoardsFromUser(accountId);
        if(noticeBoardListResponseDtos == null || noticeBoardListResponseDtos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(noticeBoardListResponseDtos);
    }

}

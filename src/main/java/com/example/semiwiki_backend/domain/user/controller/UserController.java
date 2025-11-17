package com.example.semiwiki_backend.domain.user.controller;

import com.example.semiwiki_backend.domain.notice_board.dto.response.NoticeBoardListResponseDto;
import com.example.semiwiki_backend.domain.user.dto.response.UserMyPageResponseDto;
import com.example.semiwiki_backend.domain.user.service.UserInfoService;
import com.example.semiwiki_backend.domain.user.service.UserListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserInfoService userInfoService;
    private final UserListService userListService;

    @GetMapping("{accountId}")
    public ResponseEntity<UserMyPageResponseDto> getUserMypageInfo(@PathVariable String accountId) {
        return ResponseEntity.ok().body(userInfoService.getUserInfo(accountId));
    }

    @GetMapping("{accountId}/list")
    public ResponseEntity<List<NoticeBoardListResponseDto>> getUserNoticeBoardList(@PathVariable String accountId,
                                                                                   @RequestParam(required = false) String keyword,
                                                                                   @RequestParam(required = false) List<String> categories,
                                                                                   @RequestParam(required = false, defaultValue = "recent") String orderBy,
                                                                                   @RequestParam(required = false, defaultValue = "0") int offset,
                                                                                   @RequestParam(required = false, defaultValue = "20") int limit) {
        List<NoticeBoardListResponseDto> noticeBoardListResponseDtos = userListService.getNoticeBoardsFromUser(accountId, keyword, categories, orderBy, offset, limit);
        if(noticeBoardListResponseDtos == null || noticeBoardListResponseDtos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(noticeBoardListResponseDtos);
    }

    @GetMapping("{accountId}/count")
    public ResponseEntity<Long> getUserCount(@PathVariable String accountId,
                                             @RequestParam(required = false) String keyword,
                                             @RequestParam(required = false) List<String> categories) {
        return ResponseEntity.ok().body(userListService.getNoticeBoardCount(accountId, keyword, categories));
    }

    @GetMapping("{accountId}/list/like")
    public ResponseEntity<List<NoticeBoardListResponseDto>> getUserNoticeBoardListLike(@PathVariable String accountId){
        List<NoticeBoardListResponseDto> noticeBoardListByUserWithLike = userListService.getNoticeBoardListByUserWithLike(accountId);
        return ResponseEntity.ok().body(noticeBoardListByUserWithLike);
    }
}

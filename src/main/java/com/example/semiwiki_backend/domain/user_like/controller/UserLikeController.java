package com.example.semiwiki_backend.domain.user_like.controller;

import com.example.semiwiki_backend.domain.user_like.entity.UserLike;
import com.example.semiwiki_backend.domain.user_like.service.UserLikeCreateService;
import com.example.semiwiki_backend.domain.user_like.service.UserLikeDeleteService;
import com.example.semiwiki_backend.domain.user_like.service.UserLikeReadSerivce;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/like")
@RequiredArgsConstructor
public class UserLikeController {
    private final UserLikeCreateService userLikeCreateService;
    private final UserLikeReadSerivce userLikeReadSerivce;
    private final UserLikeDeleteService userLikeDeleteService;

    @PostMapping("/{board_id}")
    public ResponseEntity<UserLike> postLike(@PathVariable int board_id, Authentication authentication) {
        UserLike userLike = userLikeCreateService.createLike(authentication, board_id);
        return ResponseEntity.ok().body(userLike);
    }

    @GetMapping("/{board_id}")
    public ResponseEntity<Boolean> isLiked(@PathVariable int board_id,Authentication authentication) {
        return ResponseEntity.ok().body(userLikeReadSerivce.isLike(authentication, board_id));
    }

    @GetMapping("/{board_id}/count")
    public ResponseEntity<Integer> countLike(@PathVariable int board_id) {
        return ResponseEntity.ok().body(userLikeReadSerivce.countAllLikes(board_id));
    }

    @DeleteMapping("/{board_id}")
    public ResponseEntity clearLike(@PathVariable int board_id,Authentication authentication) {
        userLikeDeleteService.deleteLike(authentication, board_id);
        return ResponseEntity.ok().build();
    }
}

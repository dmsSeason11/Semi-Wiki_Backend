package com.example.semiwiki_backend.domain.user_like.controller;

import com.example.semiwiki_backend.domain.user_like.entity.UserLike;
import com.example.semiwiki_backend.domain.user_like.repository.UserLikeRepository;
import com.example.semiwiki_backend.domain.user_like.service.UserLikeCreateService;
import com.example.semiwiki_backend.domain.user_like.service.UserLikeDeleteService;
import com.example.semiwiki_backend.domain.user_like.service.UserLikeReadSerivce;
import com.example.semiwiki_backend.global.security.auth.CustomUserDetails;
import com.example.semiwiki_backend.global.security.jwt.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/like")
public class UserLikeController {
    private final UserLikeCreateService userLikeCreateService;
    private final UserLikeReadSerivce userLikeReadSerivce;
    private final UserLikeDeleteService userLikeDeleteService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserLikeRepository userLikeRepository;

    public UserLikeController(UserLikeCreateService userLikeCreateService, UserLikeReadSerivce userLikeReadSerivce, UserLikeDeleteService userLikeDeleteService, JwtTokenProvider jwtTokenProvider, UserLikeRepository userLikeRepository) {
        this.userLikeCreateService = userLikeCreateService;
        this.userLikeReadSerivce = userLikeReadSerivce;
        this.userLikeDeleteService = userLikeDeleteService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userLikeRepository = userLikeRepository;
    }

    @PostMapping("/{board_id}")
    public ResponseEntity<UserLike> postLike(@PathVariable int board_id, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
        Integer userId = userDetails.getId();
        UserLike userLike = userLikeCreateService.createLike(userId, board_id);
        return ResponseEntity.ok().body(userLike);
    }

    @GetMapping("/{board_id}")
    public ResponseEntity<Boolean> isLiked(@PathVariable int board_id,Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
        Integer userId = userDetails.getId();
        return ResponseEntity.ok().body(userLikeReadSerivce.isLike(userId, board_id));
    }

    @GetMapping("/{board_id}/count")
    public ResponseEntity<Integer> countLike(@PathVariable int board_id) {
        return ResponseEntity.ok().body(userLikeReadSerivce.countAllLikes(board_id));
    }

    @DeleteMapping("/{board_id}")
    public ResponseEntity clearLike(@PathVariable int board_id,Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
        Integer userId = userDetails.getId();
        userLikeDeleteService.deleteLike(userId, board_id);
        return ResponseEntity.ok().build();
    }
}

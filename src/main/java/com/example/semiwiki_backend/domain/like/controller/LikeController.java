package com.example.semiwiki_backend.domain.like.controller;

import com.example.semiwiki_backend.domain.like.entity.Like;
import com.example.semiwiki_backend.domain.like.repository.LikeRepository;
import com.example.semiwiki_backend.domain.like.service.LikeCreateService;
import com.example.semiwiki_backend.domain.like.service.LikeDeleteService;
import com.example.semiwiki_backend.domain.like.service.LikeReadSerivce;
import com.example.semiwiki_backend.global.security.auth.CustomUserDetails;
import com.example.semiwiki_backend.global.security.jwt.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/like")
public class LikeController {
    private final LikeCreateService likeCreateService;
    private final LikeReadSerivce likeReadSerivce;
    private final LikeDeleteService likeDeleteService;
    private final JwtTokenProvider jwtTokenProvider;
    private final LikeRepository likeRepository;

    public LikeController(LikeCreateService likeCreateService, LikeReadSerivce likeReadSerivce, LikeDeleteService likeDeleteService, JwtTokenProvider jwtTokenProvider, LikeRepository likeRepository) {
        this.likeCreateService = likeCreateService;
        this.likeReadSerivce = likeReadSerivce;
        this.likeDeleteService = likeDeleteService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.likeRepository = likeRepository;
    }

    @PostMapping("/{board_id}")
    public ResponseEntity<Like> postLike(@PathVariable int board_id, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
        Integer userId = userDetails.getId();
        Like like = likeCreateService.createLike(userId, board_id);
        return ResponseEntity.ok().body(like);
    }

    @GetMapping("/{board_id}")
    public ResponseEntity<Boolean> isLiked(@PathVariable int board_id,Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
        Integer userId = userDetails.getId();
        return ResponseEntity.ok().body(likeReadSerivce.isLike(userId, board_id));
    }

    @GetMapping("/{board_id}/count")
    public ResponseEntity<Integer> countLike(@PathVariable int board_id) {
        return ResponseEntity.ok().body(likeReadSerivce.countAllLikes(board_id));
    }

    @DeleteMapping("/{board_id}")
    public ResponseEntity clearLike(@PathVariable int board_id,Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
        Integer userId = userDetails.getId();
        likeDeleteService.deleteLike(userId, board_id);
        return ResponseEntity.ok().build();
    }
}

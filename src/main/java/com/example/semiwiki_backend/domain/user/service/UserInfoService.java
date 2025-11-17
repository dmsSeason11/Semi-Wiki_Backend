package com.example.semiwiki_backend.domain.user.service;

import com.example.semiwiki_backend.domain.notice_board.repository.NoticeBoardRepository;
import com.example.semiwiki_backend.domain.user.dto.response.UserMyPageResponseDto;
import com.example.semiwiki_backend.domain.user.entity.User;
import com.example.semiwiki_backend.domain.user.exception.UserNotFoundException;
import com.example.semiwiki_backend.domain.user.repository.UserRepository;
import com.example.semiwiki_backend.domain.user_notice_board.repository.UserNoticeBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserInfoService {
    private final UserRepository userRepository;
    private final UserNoticeBoardRepository userNoticeBoardRepository;
    private final NoticeBoardRepository noticeBoardRepository;

    //유저 마이페이지 정보 반환
    public UserMyPageResponseDto getUserInfo(String accountId) {
        User user = userRepository.findByAccountId(accountId).orElseThrow(() -> new UserNotFoundException());
        int noticeBoardcount = userNoticeBoardRepository.countUserNoticeBoardsByUser(user); //게시판과의 관계의 개수를 셈 == 기여한 게시판 수

        return  UserMyPageResponseDto.builder()
                .noticeBoardCount(noticeBoardcount)
                .accountId(user.getAccountId())
                .build();
    }

}

package com.example.semiwiki_backend.domain.notice_board.service;

import com.example.semiwiki_backend.domain.notice_board.dto.request.NoticeBoardUpdateRequestDto;
import com.example.semiwiki_backend.domain.notice_board.dto.response.NoticeBoardDetailResponseDto;
import com.example.semiwiki_backend.domain.notice_board.entity.NoticeBoard;
import com.example.semiwiki_backend.domain.notice_board.exception.NoticeBoardNotFoundException;
import com.example.semiwiki_backend.domain.notice_board.repository.NoticeBoardRepository;
import com.example.semiwiki_backend.domain.user.entity.User;
import com.example.semiwiki_backend.domain.user.exception.UserNotFoundException;
import com.example.semiwiki_backend.domain.user.repository.UserRepository;
import com.example.semiwiki_backend.domain.user_notice_board.entity.UserNoticeBoard;
import com.example.semiwiki_backend.domain.user_notice_board.repository.UserNoticeBoardRepository;
import com.example.semiwiki_backend.global.security.auth.CustomUserDetails;
import com.example.semiwiki_backend.global.security.exception.JwtExpiredException;
import com.example.semiwiki_backend.global.security.exception.JwtInvalidException;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeBoardUpdateService {
    private final NoticeBoardRepository noticeBoardRepository;
    private final UserRepository userRepository;
    private final UserNoticeBoardRepository userNoticeBoardRepository;


    @Transactional
    public NoticeBoardDetailResponseDto updateNoticeBoard(NoticeBoardUpdateRequestDto dto, Integer id, Authentication authentication) {
        //유저 아이디 jwt토큰에서 가져옴
        CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
        Integer userId = userDetails.getId();

        //user, noticeBoard 불러옴
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException());
        NoticeBoard noticeBoard = noticeBoardRepository.findById(id)
                .orElseThrow(() -> new NoticeBoardNotFoundException());

        //유저가 기여했는지 확인, 기여 안된경우 기여한목록에 추가
        List<UserNoticeBoard> userNoticeBoardList = noticeBoard.getUsers();
        boolean userExists = userNoticeBoardList.stream() // user가 있는지 확인
                .anyMatch(unt -> unt.getUser().getId() == user.getId());

        if (!userExists) { // 없는경우에 실행
            UserNoticeBoard newUserNoticeBoard = userNoticeBoardRepository.save(
                    UserNoticeBoard.builder()
                            .user(user)
                            .noticeBoard(noticeBoard)
                            .build()
            );
        }

        userNoticeBoardList.addAll(noticeBoard.getUsers());
        NoticeBoard changingBoard = noticeBoardRepository.save(NoticeBoard.builder()
                .title(dto.getTitle())
                .id(noticeBoard.getId())
                .categories(dto.getCategories())
                .contents(dto.getContents())
                .createdAt(noticeBoard.getCreatedAt())
                .modficatedAt(noticeBoard.getModficatedAt())
                .users(userNoticeBoardList).build());

        //반환용
        List<User> users = new ArrayList<>();
        for (UserNoticeBoard userNotice : userNoticeBoardList)
            users.add(userNotice.getUser());

        return NoticeBoardDetailResponseDto.builder()
                .title(noticeBoard.getTitle())
                .contents(noticeBoard.getContents())
                .categories(noticeBoard.getCategories())
                .createdAt(noticeBoard.getCreatedAt())
                .modficatedAt(noticeBoard.getModficatedAt())
                .users(users).build();
    }

}

package com.example.semiwiki_backend.domain.notice_board.service;

import com.example.semiwiki_backend.domain.notice_board.dto.request.NoticeBoardCreateRequestDto;
import com.example.semiwiki_backend.domain.notice_board.dto.response.NoticeBoardDetailResponseDto;
import com.example.semiwiki_backend.domain.notice_board.entity.NoticeBoard;
import com.example.semiwiki_backend.domain.notice_board.exception.NoCategoryException;
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
public class NoticeBoardCreateService {
    private final NoticeBoardRepository noticeBoardRepository;
    private final UserRepository userRepository;
    private final UserNoticeBoardRepository userNoticeBoardRepository;


    @Transactional
    public NoticeBoardDetailResponseDto createNoticeBoard(NoticeBoardCreateRequestDto dto, Authentication authentication)
    {
        //유저 아이디 jwt토큰에서 가져옴
        Integer userId = null;
        try {
            CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
            userId = userDetails.getId();
        } catch (ExpiredJwtException e){
            throw new JwtExpiredException();
        } catch (Exception e) {
            throw new JwtInvalidException();
        }

        List<String> categories = dto.getCategories();
        if (categories == null || categories.isEmpty())
            throw new NoCategoryException();
        //noticeBoard 저장
        NoticeBoard noticeBoard = noticeBoardRepository.save(NoticeBoard.builder()
                .title(dto.getTitle())
                .contents(dto.getContents())
                .categories(categories)
                .build());
        //UserNoticeTable에 관계 저장
        UserNoticeBoard userNoticeBoard = UserNoticeBoard.builder()
                .user(userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException()))
                .noticeBoard(noticeBoard).build();
        userNoticeBoardRepository.save(userNoticeBoard);
        //noticeBoard에도 바뀐것 저장
        noticeBoard.addUserNotice(userNoticeBoard);

        //반환 위해서 유저 리스트 만듬
        List<User> users = new ArrayList<>();
        for (UserNoticeBoard userNotice : noticeBoard.getUsers())
            users.add(userNotice.getUser());

        //반환은 detail로
        return NoticeBoardDetailResponseDto.builder()
                .title(noticeBoard.getTitle())
                .contents(noticeBoard.getContents())
                .createdAt(noticeBoard.getCreatedAt())
                .modficatedAt(noticeBoard.getModficatedAt())
                .users(users)
                .categories(noticeBoard.getCategories())
                .build();
    }
}

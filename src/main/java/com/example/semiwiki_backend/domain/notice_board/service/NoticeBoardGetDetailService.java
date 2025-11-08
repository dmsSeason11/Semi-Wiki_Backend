package com.example.semiwiki_backend.domain.notice_board.service;

import com.example.semiwiki_backend.domain.notice_board.dto.response.NoticeBoardDetailResponseDto;
import com.example.semiwiki_backend.domain.notice_board.entity.NoticeBoard;
import com.example.semiwiki_backend.domain.notice_board.exception.NoticeBoardNotFoundException;
import com.example.semiwiki_backend.domain.notice_board.repository.NoticeBoardRepository;
import com.example.semiwiki_backend.domain.user.entity.User;
import com.example.semiwiki_backend.domain.user_notice_board.entity.UserNoticeBoard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeBoardGetDetailService {
    private final NoticeBoardRepository noticeBoardRepository;


    public NoticeBoardDetailResponseDto getNoticeBoard(Integer noticeBoardId) {
        NoticeBoard noticeBoard = noticeBoardRepository.findById(noticeBoardId).orElseThrow(NoticeBoardNotFoundException::new);
        List<User> users = new ArrayList<>();
        List<UserNoticeBoard> userNoticeBoardList = noticeBoard.getUsers();
        for(UserNoticeBoard userNoticeBoard : userNoticeBoardList)
            users.add(userNoticeBoard.getUser());

        return NoticeBoardDetailResponseDto.builder()
                .title(noticeBoard.getTitle())
                .noticeBoardHeaders(noticeBoard.getNoticeBoardHeaders())
                .createdAt(noticeBoard.getCreatedAt())
                .modficatedAt(noticeBoard.getModficatedAt())
                .users(users)
                .contents(noticeBoard.getContents())
                .categories(noticeBoard.getCategories())
                .build();
    }

}

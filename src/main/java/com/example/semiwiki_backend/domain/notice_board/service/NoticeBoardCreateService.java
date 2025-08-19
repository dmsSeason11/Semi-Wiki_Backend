package com.example.semiwiki_backend.domain.notice_board.service;

import com.example.semiwiki_backend.domain.notice_board.dto.request.NoticeBoardCreateRequestDto;
import com.example.semiwiki_backend.domain.notice_board.entity.NoticeBoard;
import com.example.semiwiki_backend.domain.notice_board.exception.NoCategoryException;
import com.example.semiwiki_backend.domain.notice_board.repository.NoticeBoardRepository;
import com.example.semiwiki_backend.domain.user.exception.UserNotFoundException;
import com.example.semiwiki_backend.domain.user.repository.UserRepository;
import com.example.semiwiki_backend.domain.user_notice_board.entity.UserNoticeBoard;
import com.example.semiwiki_backend.domain.user_notice_board.repository.UserNoticeBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class NoticeBoardCreateService {
    private final NoticeBoardRepository noticeBoardRepository;
    private final UserRepository userRepository;
    private final UserNoticeBoardRepository userNoticeBoardRepository;


    @Transactional
    public NoticeBoard createNoticeBoard(NoticeBoardCreateRequestDto dto,Integer userId) {
        List<String> categories = dto.getCategories();
        if (categories == null || categories.isEmpty())
            throw new NoCategoryException();
        NoticeBoard noticeBoard = noticeBoardRepository.save(NoticeBoard.builder()
                .title(dto.getTitle())
                .contents(dto.getContents())
                .categories(categories)
                .build());
        UserNoticeBoard userNoticeBoard = UserNoticeBoard.builder()
                .user(userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException()))
                .noticeBoard(noticeBoard).build();
        noticeBoard.addUserNotice(userNoticeBoard);
        userNoticeBoardRepository.save(userNoticeBoard);
        return noticeBoard;
    }
}

package com.example.semiwiki_backend.domain.notice_board.service;

import com.example.semiwiki_backend.domain.notice_board.dto.request.NoticeBoardCreateRequestDto;
import com.example.semiwiki_backend.domain.notice_board.entity.NoticeBoard;
import com.example.semiwiki_backend.domain.notice_board.exception.NoCategoryException;
import com.example.semiwiki_backend.domain.notice_board.repository.NoticeBoardRepository;
import com.example.semiwiki_backend.domain.user.exception.UserNotFoundException;
import com.example.semiwiki_backend.domain.user.repository.UserRepository;
import com.example.semiwiki_backend.domain.user_notice_board.entity.UserNoticeBoard;
import com.example.semiwiki_backend.domain.user_notice_board.repository.UserNoticeBoardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class NoticeBoardCreateService {
    private final NoticeBoardRepository noticeBoardRepository;

    private final UserRepository userRepository;

    private final UserNoticeBoardRepository userNoticeBoardRepository;
    public NoticeBoardCreateService(NoticeBoardRepository noticeBoardRepository, UserRepository userRepository, UserNoticeBoardRepository userNoticeBoardRepository) {
        this.noticeBoardRepository = noticeBoardRepository;
        this.userRepository = userRepository;
        this.userNoticeBoardRepository = userNoticeBoardRepository;
    }

    @Transactional
    public NoticeBoard createNoticeBoard(NoticeBoardCreateRequestDto dto) {
        List<String> categories = dto.getCategories();
        if (categories == null || categories.isEmpty())
            throw new NoCategoryException("카테고리가 없습니다.");
        NoticeBoard noticeBoard = noticeBoardRepository.save(NoticeBoard.builder()
                .title(dto.getTitle())
                .contents(dto.getContents())
                .categories(categories)
                .build());
        UserNoticeBoard userNoticeBoard = UserNoticeBoard.builder()
                .user(userRepository.findById(dto.getUserId()).orElseThrow(() -> new UserNotFoundException("유저를 찾지 못했습니다.")))
                .noticeBoard(noticeBoard).build();
        noticeBoard.addUserNotice(userNoticeBoard);
        userNoticeBoardRepository.save(userNoticeBoard);
        return noticeBoard;
    }
}

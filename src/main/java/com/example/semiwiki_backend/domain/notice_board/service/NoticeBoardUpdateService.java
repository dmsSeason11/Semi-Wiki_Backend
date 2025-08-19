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
import lombok.RequiredArgsConstructor;
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
    public NoticeBoardDetailResponseDto updateNoticeBoard(NoticeBoardUpdateRequestDto dto, Integer id, Integer userId) {
        NoticeBoard noticeBoard = noticeBoardRepository.findById(id)
                .orElseThrow(() -> new NoticeBoardNotFoundException());

        noticeBoard.setTitle(dto.getTitle());
        noticeBoard.setContents(dto.getContents());
        noticeBoard.setCategories(dto.getCategories());

        List<UserNoticeBoard> userNoticeBoardList = noticeBoard.getUsers();

        User userToAdd = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException()); // Users에 추가할 User

        boolean userExists = userNoticeBoardList.stream() // user가 있는지 확인
                .anyMatch(unt -> unt.getUser().getId() == userToAdd.getId());

        if (!userExists) { // 없는경우에 실행
            UserNoticeBoard newUserNoticeBoard = userNoticeBoardRepository.save(
                    UserNoticeBoard.builder()
                            .user(userToAdd)
                            .noticeBoard(noticeBoard)
                            .build()
            );
            userNoticeBoardList.add(newUserNoticeBoard);
        }

        List<User> users = new ArrayList<>();
        for (UserNoticeBoard userNotice : userNoticeBoardList)
            users.add(userNotice.getUser());

        return NoticeBoardDetailResponseDto.builder()
                .title(noticeBoard.getTitle())
                .contents(noticeBoard.getContents())
                .categories(noticeBoard.getCategories())
                .users(users).build();
    }

}

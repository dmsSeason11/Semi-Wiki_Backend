package com.example.semiwiki_backend.domain.notice_board.service;

import com.example.semiwiki_backend.domain.notice_board.dto.request.NoticeBoardUpdateRequest;
import com.example.semiwiki_backend.domain.notice_board.dto.response.NoticeBoardDetailResponseDto;
import com.example.semiwiki_backend.domain.notice_board.entity.NoticeBoard;
import com.example.semiwiki_backend.domain.notice_board.exception.NoticeBoardNotFoundException;
import com.example.semiwiki_backend.domain.notice_board.repository.NoticeBoardRepository;
import com.example.semiwiki_backend.domain.user.entity.User;
import com.example.semiwiki_backend.domain.user.exception.UserNotFoundException;
import com.example.semiwiki_backend.domain.user.repository.UserRepository;
import com.example.semiwiki_backend.domain.user_notice_board.entity.UserNoticeBoard;
import com.example.semiwiki_backend.domain.user_notice_board.repository.UserNoticeBoardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoticeBoardUpdateService {
    private final NoticeBoardRepository noticeBoardRepository;
    private final UserRepository userRepository;
    private final UserNoticeBoardRepository userNoticeBoardRepository;

    public NoticeBoardUpdateService(NoticeBoardRepository noticeBoardRepository, UserRepository userRepository, UserNoticeBoardRepository userNoticeBoardRepository) {
        this.noticeBoardRepository = noticeBoardRepository;
        this.userRepository = userRepository;
        this.userNoticeBoardRepository = userNoticeBoardRepository;
    }

    @Transactional
    public NoticeBoardDetailResponseDto updateNoticeBoard(NoticeBoardUpdateRequest dto, Integer id){
        NoticeBoard noticeBoard = noticeBoardRepository.findById(id)
                .orElseThrow(() -> new NoticeBoardNotFoundException("id : " + id + " 를 찾을수 없습니다."));

        noticeBoard.setTitle(dto.getTitle());
        noticeBoard.setContents(dto.getContents());
        noticeBoard.setCategories(dto.getCategories());

        List<UserNoticeBoard> userNoticeBoardList = noticeBoard.getUsers();

        User userToAdd = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("유저를 찾을수 없습니다.")); // Users에 추가할 User

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

package com.example.semiwiki_backend.domain.notice_board.service;

import com.example.semiwiki_backend.domain.notice_board.dto.request.NoticeBoardHeaderUpdateRequestDto;
import com.example.semiwiki_backend.domain.notice_board.dto.response.NoticeBoardDetailResponseDto;
import com.example.semiwiki_backend.domain.notice_board.entity.NoticeBoard;
import com.example.semiwiki_backend.domain.notice_board.entity.NoticeBoardHeader;
import com.example.semiwiki_backend.domain.notice_board.exception.HeaderNotFoundException;
import com.example.semiwiki_backend.domain.notice_board.exception.NoticeBoardNotFoundException;
import com.example.semiwiki_backend.domain.notice_board.repository.NoticeBoardHeaderRepository;
import com.example.semiwiki_backend.domain.notice_board.repository.NoticeBoardRepository;
import com.example.semiwiki_backend.domain.user.entity.User;
import com.example.semiwiki_backend.domain.user.exception.UserNotFoundException;
import com.example.semiwiki_backend.domain.user.repository.UserRepository;
import com.example.semiwiki_backend.domain.user_notice_board.entity.UserNoticeBoard;
import com.example.semiwiki_backend.domain.user_notice_board.repository.UserNoticeBoardRepository;
import com.example.semiwiki_backend.global.security.auth.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeBoardHeaderUpdateService {
    private final NoticeBoardRepository noticeBoardRepository;
    private final UserRepository userRepository;
    private final UserNoticeBoardRepository userNoticeBoardRepository;
    private final NoticeBoardHeaderRepository noticeBoardHeaderRepository;


    @Transactional
    public NoticeBoardDetailResponseDto updateNoticeBoard(NoticeBoardHeaderUpdateRequestDto dto, Integer id, Authentication authentication ) {
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
        boolean userExists = userNoticeBoardRepository.existsUserNoticeBoardByUserAndNoticeBoard(user,noticeBoard);

        if (!userExists) { // 없는경우에 실행
            UserNoticeBoard newUserNoticeBoard = userNoticeBoardRepository.save(
                    UserNoticeBoard.builder()
                            .user(user)
                            .noticeBoard(noticeBoard)
                            .build()
            );
            userNoticeBoardList.add(newUserNoticeBoard);
        }
        NoticeBoardHeader noticeBoardHeader = noticeBoardHeaderRepository.findById(dto.getHeaderId()).orElseThrow(() -> new HeaderNotFoundException());
        noticeBoardHeader.updateHeaderContents(dto.getContents(), dto.getTitle());
        noticeBoardHeaderRepository.save(noticeBoardHeader);

        noticeBoard.updateUserNoticeAndCategories(userNoticeBoardList,dto.getCategories());
        noticeBoardRepository.save(noticeBoard);

        //반환용
        List<User> users = new ArrayList<>();
        for (UserNoticeBoard userNotice : userNoticeBoardList)
            users.add(userNotice.getUser());

        return NoticeBoardDetailResponseDto.builder()
                .title(noticeBoard.getTitle())
                .noticeBoardHeaders(noticeBoard.getNoticeBoardHeaders())
                .categories(noticeBoard.getCategories())
                .createdAt(noticeBoard.getCreatedAt())
                .modficatedAt(noticeBoard.getModficatedAt())
                .users(users).build();
    }

}

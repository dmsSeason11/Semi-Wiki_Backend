package com.example.semiwiki_backend.domain.user.service;

import com.example.semiwiki_backend.domain.notice_board.dto.response.NoticeBoardListResponseDto;
import com.example.semiwiki_backend.domain.notice_board.entity.NoticeBoard;
import com.example.semiwiki_backend.domain.notice_board.repository.NoticeBoardRepository;
import com.example.semiwiki_backend.domain.user.dto.response.UserMyPageResponseDto;
import com.example.semiwiki_backend.domain.user.dto.response.UserPreviewResponseDto;
import com.example.semiwiki_backend.domain.user.entity.User;
import com.example.semiwiki_backend.domain.user.exception.UserNotFoundException;
import com.example.semiwiki_backend.domain.user.repository.UserRepository;
import com.example.semiwiki_backend.domain.user_notice_board.entity.UserNoticeBoard;
import com.example.semiwiki_backend.domain.user_notice_board.repository.UserNoticeBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserReadService {
    private final UserRepository userRepository;
    private final UserNoticeBoardRepository userNoticeBoardRepository;
    private final NoticeBoardRepository noticeBoardRepository;

    //유저 마이페이지 정보 반환
    public UserMyPageResponseDto GetUserInfo(int userId) {
        //유저 탐색
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("유저를 찾을수 없습니다."));
        int noticeBoardcount = userNoticeBoardRepository.countUserNoticeBoardsByUser(user); //게시판과의 관계의 개수를 셈 == 기여한 게시판 수

        return  UserMyPageResponseDto.builder()
                .noticeBoardCount(noticeBoardcount)
                .accountId(user.getAccountId())
                .build();
    }

    //유저가 기여한(제작, 수정) 게시글 목록
    public List<NoticeBoardListResponseDto> GetNoticeBoardsFromUser(int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("유저를 찾을수 없습니다."));
        List<UserNoticeBoard> userNoticeBoards = user.getNoticeBoards();
        List<NoticeBoardListResponseDto> noticeBoardListResponseDtos = new ArrayList<>();

        for(UserNoticeBoard userNoticeBoard : userNoticeBoards) {
            UserPreviewResponseDto userPreviewResponseDto = UserPreviewResponseDto.builder()
                    .userId(user.getId())
                    .accountId(user.getAccountId())
                    .build();
            NoticeBoardListResponseDto noticeBoardListResponseDto = NoticeBoardListResponseDto.builder()
                    .id(userNoticeBoard.getNoticeBoard().getId())
                    .title(userNoticeBoard.getNoticeBoard().getTitle())
                    .categories(userNoticeBoard.getNoticeBoard().getCategories())
                    .userPreview(userPreviewResponseDto)
                    .build();
            noticeBoardListResponseDtos.add(noticeBoardListResponseDto);
        }
        return noticeBoardListResponseDtos;
    }
}

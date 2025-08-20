package com.example.semiwiki_backend.domain.notice_board.service;

import com.example.semiwiki_backend.domain.notice_board.dto.response.NoticeBoardDetailResponseDto;
import com.example.semiwiki_backend.domain.notice_board.dto.response.NoticeBoardListResponseDto;
import com.example.semiwiki_backend.domain.notice_board.entity.NoticeBoard;
import com.example.semiwiki_backend.domain.notice_board.exception.NoticeBoardNotFoundException;
import com.example.semiwiki_backend.domain.notice_board.repository.NoticeBoardRepository;
import com.example.semiwiki_backend.domain.user.dto.response.UserPreviewResponseDto;
import com.example.semiwiki_backend.domain.user.entity.User;
import com.example.semiwiki_backend.domain.user_notice_board.entity.UserNoticeBoard;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeBoardGetService {
    private final NoticeBoardRepository noticeBoardRepository;


    public NoticeBoardDetailResponseDto getNoticeBoard(Integer noticeBoardId) {
        NoticeBoard noticeBoard = noticeBoardRepository.findById(noticeBoardId).orElseThrow(() -> new NoticeBoardNotFoundException());
        List<User> users = new ArrayList<>();
        List<UserNoticeBoard> userNoticeBoardList = noticeBoard.getUsers();
        for(UserNoticeBoard userNoticeBoard : userNoticeBoardList)
            users.add(userNoticeBoard.getUser());

        return NoticeBoardDetailResponseDto.builder()
                .title(noticeBoard.getTitle())
                .contents(noticeBoard.getContents())
                .createdAt(noticeBoard.getCreatedAt())
                .modficatedAt(noticeBoard.getModficatedAt())
                .users(users)
                .categories(noticeBoard.getCategories())
                .build();
    }

    public List<NoticeBoardListResponseDto> getNoticeBoardListByCategories(List<String> categories, int offset, int limit) {
        List<NoticeBoard> noticeBoardList = noticeBoardRepository.findByCategoriesAllMatch(categories, categories.size(), PageRequest.of(offset, limit));
        return getNoticeBoardListResponseDtos(noticeBoardList);
    }

    public List<NoticeBoardListResponseDto> getAllNoticeBoards(int offset, int limit) {
        List<NoticeBoard> noticeBoardList = noticeBoardRepository.findAllNoticeBoards(PageRequest.of(offset, limit));
        return getNoticeBoardListResponseDtos(noticeBoardList);
    }



    public List<NoticeBoardListResponseDto> searchNoticeBoards(String keyword, int offset, int limit) {
        List<NoticeBoard> noticeBoardList = noticeBoardRepository.findByTitleContainingIgnoreCase(keyword,PageRequest.of(offset, limit));
        return getNoticeBoardListResponseDtos(noticeBoardList);
    }

    public List<NoticeBoardListResponseDto> searchAndFindByCategoryNoticeBoards(String keyword, List<String> categories, int offset, int limit) {
        List<NoticeBoard> noticeBoardList = noticeBoardRepository.findByTitleContainingAndCategoriesAllMatch(keyword, categories, categories.size(), PageRequest.of(offset, limit));
        return getNoticeBoardListResponseDtos(noticeBoardList);
    }

    private List<NoticeBoardListResponseDto> getNoticeBoardListResponseDtos(List<NoticeBoard> noticeBoardList) {
        List<NoticeBoardListResponseDto> noticeBoardListDto = new ArrayList<>();
        for(NoticeBoard noticeBoard : noticeBoardList) {
            List<User> users = new ArrayList<>();
            List<UserNoticeBoard> userNoticeBoardList = noticeBoard.getUsers();
            for(UserNoticeBoard userNoticeBoard : userNoticeBoardList)
                users.add(userNoticeBoard.getUser());

            int usersLength = users.size();
            UserPreviewResponseDto userPreviewResponseDto = UserPreviewResponseDto.builder()
                    .userId(users.get(usersLength-1).getId())
                    .accountId(users.get(usersLength-1).getAccountId())
                    .build();

            noticeBoardListDto.add(NoticeBoardListResponseDto.builder()
                    .id(noticeBoard.getId())
                    .categories(noticeBoard.getCategories())
                    .title(noticeBoard.getTitle())
                    .userPreview(userPreviewResponseDto)
                    .build());
        }
        return noticeBoardListDto;
    }
}

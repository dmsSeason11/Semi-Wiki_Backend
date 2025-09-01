package com.example.semiwiki_backend.domain.notice_board.service;

import com.example.semiwiki_backend.domain.notice_board.dto.request.NoticeBoardListDto;
import com.example.semiwiki_backend.domain.notice_board.dto.response.NoticeBoardListResponseDto;
import com.example.semiwiki_backend.domain.notice_board.entity.NoticeBoard;
import com.example.semiwiki_backend.domain.notice_board.exception.IncorrectOrderByException;
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
public class NoticeBoardGetListService {
    private final NoticeBoardRepository noticeBoardRepository;

    public List<NoticeBoardListResponseDto> getNoticeBoardList(NoticeBoardListDto noticeBoardListDto) {
        final List<String> categories = noticeBoardListDto.getCategories();
        final String keyword = noticeBoardListDto.getKeyword();
        final int offset = noticeBoardListDto.getOffset();
        final int limit = noticeBoardListDto.getLimit();

        if(noticeBoardListDto.getOrderBy().equals("recent")){ //최신순
            if(categories == null || categories.isEmpty()) {
                //카테고리랑 키워드 둘다 없는경우
                if(keyword == null || keyword.isEmpty()){
                    List<NoticeBoard> noticeBoardList = noticeBoardRepository.findAllNoticeBoards(PageRequest.of(offset, limit));
                    return getNoticeBoardListResponseDtos(noticeBoardList);
                }
                //검색 키워드만 있는경우
                List<NoticeBoard> noticeBoardList = noticeBoardRepository.findByTitleContainingIgnoreCase(keyword,PageRequest.of(offset, limit));
                return getNoticeBoardListResponseDtos(noticeBoardList);
            }
            //카테고리만 있는경우
            if(keyword == null || keyword.isEmpty()){
                List<NoticeBoard> noticeBoardList = noticeBoardRepository.findByCategoriesAllMatch(categories, categories.size(), PageRequest.of(offset, limit));
                return getNoticeBoardListResponseDtos(noticeBoardList);
            }
            //둘 다 있는 경우
            List<NoticeBoard> noticeBoardList = noticeBoardRepository.findByTitleContainingAndCategoriesAllMatch(keyword, categories, categories.size(), PageRequest.of(offset, limit));
            return getNoticeBoardListResponseDtos(noticeBoardList);
        }
        else if(noticeBoardListDto.getOrderBy().equals("like")){
            if(categories == null || categories.isEmpty()) {
                //카테고리랑 키워드 둘다 없는경우
                if(keyword == null || keyword.isEmpty()){
                    List<NoticeBoard> noticeBoardList = noticeBoardRepository.findAllOrderByLikeCountDescThenCreatedAtDesc(PageRequest.of(offset, limit));
                    return getNoticeBoardListResponseDtos(noticeBoardList);
                }
                //검색 키워드만 있는경우
                List<NoticeBoard> noticeBoardList = noticeBoardRepository.findByTitleContainingIgnoreCaseOrderByLikeCountDesc(keyword,PageRequest.of(offset, limit));
                return getNoticeBoardListResponseDtos(noticeBoardList);
            }
            //카테고리만 있는경우
            if(keyword == null || keyword.isEmpty()){
                List<NoticeBoard> noticeBoardList = noticeBoardRepository.findByCategoriesAllMatchOrderByLikeCountDesc(categories, categories.size(), PageRequest.of(offset, limit));
                return getNoticeBoardListResponseDtos(noticeBoardList);
            }
            //둘 다 있는 경우
            List<NoticeBoard> noticeBoardList = noticeBoardRepository.findByTitleContainingIgnoreCaseAndCategoriesOrderByLikeCountDesc(keyword, categories, categories.size(), PageRequest.of(offset, limit));
            return getNoticeBoardListResponseDtos(noticeBoardList);
        }
        else
            throw new IncorrectOrderByException();
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

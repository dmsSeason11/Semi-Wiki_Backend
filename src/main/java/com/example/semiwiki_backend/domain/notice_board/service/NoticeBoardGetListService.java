package com.example.semiwiki_backend.domain.notice_board.service;

import com.example.semiwiki_backend.domain.notice_board.dto.response.NoticeBoardListResponseDto;
import com.example.semiwiki_backend.domain.notice_board.entity.NoticeBoard;
import com.example.semiwiki_backend.domain.notice_board.exception.IncorrectOrderByException;
import com.example.semiwiki_backend.domain.notice_board.repository.NoticeBoardRepository;
import com.example.semiwiki_backend.domain.user.dto.response.UserPreviewResponseDto;
import com.example.semiwiki_backend.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeBoardGetListService {
    private final NoticeBoardRepository noticeBoardRepository;

    public List<NoticeBoardListResponseDto> getNoticeBoardList(List<String> categories,String keyword,int offset,int limit,String orderBy) {

        if(orderBy.equals("recent")){ //최신순
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
        else if(orderBy.equals("like")){
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
            User modifier = noticeBoard.getUsers().get(noticeBoard.getUsers().size()-1).getUser();
            UserPreviewResponseDto userPreviewResponseDto = UserPreviewResponseDto.builder()
                    .userId(modifier.getId()) // 마지막 수정자 기준
                    .accountId(modifier.getAccountId())
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

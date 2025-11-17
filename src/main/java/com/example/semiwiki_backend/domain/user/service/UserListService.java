package com.example.semiwiki_backend.domain.user.service;

import com.example.semiwiki_backend.domain.notice_board.dto.response.NoticeBoardListResponseDto;
import com.example.semiwiki_backend.domain.notice_board.entity.NoticeBoard;
import com.example.semiwiki_backend.domain.notice_board.exception.IncorrectOrderByException;
import com.example.semiwiki_backend.domain.user.dto.response.UserPreviewResponseDto;
import com.example.semiwiki_backend.domain.user.entity.User;
import com.example.semiwiki_backend.domain.user.exception.UserNotFoundException;
import com.example.semiwiki_backend.domain.user.repository.UserRepository;
import com.example.semiwiki_backend.domain.user_notice_board.repository.UserNoticeBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserListService {
    private final UserNoticeBoardRepository userNoticeBoardRepository;
    private final UserRepository userRepository;

    public List<NoticeBoardListResponseDto> getNoticeBoardListByUserWithLike(String accountId){
        User user = userRepository.findByAccountId(accountId).orElseThrow(() -> new UserNotFoundException());

        List<NoticeBoard> noticeBoards = userNoticeBoardRepository.findAllByUserWithLike(accountId);

        List<NoticeBoardListResponseDto> noticeBoardListResponseDtos = new ArrayList<>();
        for (NoticeBoard noticeBoard : noticeBoards) {
            User modifier = noticeBoard.getUsers().get(noticeBoard.getUsers().size()-1).getUser();
            UserPreviewResponseDto userPreviewResponseDto = UserPreviewResponseDto.builder()
                    .userId(modifier.getId()) // 마지막 수정자 기준
                    .accountId(modifier.getAccountId())
                    .build();

            NoticeBoardListResponseDto noticeBoardListResponseDto = NoticeBoardListResponseDto.builder()
                    .id(noticeBoard.getId())
                    .title(noticeBoard.getTitle())
                    .categories(noticeBoard.getCategories())
                    .userPreview(userPreviewResponseDto)
                    .build();
            noticeBoardListResponseDtos.add(noticeBoardListResponseDto);
        }
        return noticeBoardListResponseDtos;
    }


    //유저가 기여한(제작, 수정) 게시글 목록
    public List<NoticeBoardListResponseDto> getNoticeBoardsFromUser(String accountId, String keyword, List<String> categories, String orderBy, int offset, int limit) {

        User user = userRepository.findByAccountId(accountId).orElseThrow(() -> new UserNotFoundException());

        List<NoticeBoard> noticeBoards = new ArrayList<>();
        if(orderBy.equals("recent")){
            if(categories == null || categories.isEmpty()) {
                //카테고리랑 키워드 둘다 없는경우
                if(keyword == null || keyword.isEmpty()){
                    noticeBoards = userNoticeBoardRepository.findAllByUserOrderByCreatedAtDesc(accountId, PageRequest.of(offset, limit));
                }
                //검색 키워드만 있는경우
                else
                    noticeBoards = userNoticeBoardRepository.findByUserAndTitleContainingIgnoreCase(accountId, keyword,PageRequest.of(offset, limit));
            }
            //카테고리만 있는경우
            else if(keyword == null || keyword.isEmpty()){
                noticeBoards = userNoticeBoardRepository.findByUserAndCategoriesAllMatch(accountId, categories, categories.size(), PageRequest.of(offset, limit));
            }
            else{
                noticeBoards = userNoticeBoardRepository.findByUserAndTitleContainingAndCategoriesAllMatch(accountId,keyword, categories, categories.size(), PageRequest.of(offset, limit));
            }

        }
        else if(orderBy.equals("like")){
            if(categories == null || categories.isEmpty()) {
                //카테고리랑 키워드 둘다 없는경우
                if(keyword == null || keyword.isEmpty()){
                    noticeBoards = userNoticeBoardRepository.findAllByUserOrderByLikeCountDescThenCreatedAtDesc(accountId,PageRequest.of(offset, limit));
                }
                else
                    //검색 키워드만 있는경우
                    noticeBoards = userNoticeBoardRepository.findByUserAndTitleContainingIgnoreCaseOrderByLikeCountDesc(accountId,keyword,PageRequest.of(offset, limit));
            }
            //카테고리만 있는경우
            else if(keyword == null || keyword.isEmpty()){
                noticeBoards = userNoticeBoardRepository.findByUserAndCategoriesAllMatchOrderByLikeCountDesc(accountId, categories, categories.size(), PageRequest.of(offset, limit));
            }
            else {
                noticeBoards = userNoticeBoardRepository.findByUserAndTitleContainingAndCategoriesOrderByLikeCountDesc(accountId, keyword, categories, categories.size(), PageRequest.of(offset, limit));
            }

        }
        else
            throw new IncorrectOrderByException();
        List<NoticeBoardListResponseDto> noticeBoardListResponseDtos = new ArrayList<>();
        for (NoticeBoard noticeBoard : noticeBoards) {
            User modifier = noticeBoard.getUsers().get(noticeBoard.getUsers().size()-1).getUser();
            UserPreviewResponseDto userPreviewResponseDto = UserPreviewResponseDto.builder()
                    .userId(modifier.getId()) // 마지막 수정자 기준
                    .accountId(modifier.getAccountId())
                    .build();

            NoticeBoardListResponseDto noticeBoardListResponseDto = NoticeBoardListResponseDto.builder()
                    .id(noticeBoard.getId())
                    .title(noticeBoard.getTitle())
                    .categories(noticeBoard.getCategories())
                    .userPreview(userPreviewResponseDto)
                    .build();

            noticeBoardListResponseDtos.add(noticeBoardListResponseDto);
        }
        return noticeBoardListResponseDtos;

    }

    public Long getNoticeBoardCount(String accountId, String keyword, List<String> categories) {
        if(keyword == null) {
            if (categories == null)
                return userNoticeBoardRepository.countAllByUser(accountId);

            return userNoticeBoardRepository.countByUserAndCategoriesAllMatch(accountId,categories, categories.size());
        }
        if(categories == null)
            return userNoticeBoardRepository.countByUserAndTitleContainingIgnoreCase(accountId,keyword);

        return userNoticeBoardRepository.countByUserAndTitleContainingAndCategoriesAllMatch(accountId, keyword,categories, categories.size());
    }
}

package com.example.semiwiki_backend.domain.user.service;

import com.example.semiwiki_backend.domain.notice_board.dto.request.NoticeBoardCountRequestDto;
import com.example.semiwiki_backend.domain.notice_board.dto.request.NoticeBoardListDto;
import com.example.semiwiki_backend.domain.notice_board.dto.response.NoticeBoardListResponseDto;
import com.example.semiwiki_backend.domain.notice_board.entity.NoticeBoard;
import com.example.semiwiki_backend.domain.notice_board.exception.IncorrectOrderByException;
import com.example.semiwiki_backend.domain.notice_board.repository.NoticeBoardRepository;
import com.example.semiwiki_backend.domain.user.dto.response.UserMyPageResponseDto;
import com.example.semiwiki_backend.domain.user.dto.response.UserPreviewResponseDto;
import com.example.semiwiki_backend.domain.user.entity.User;
import com.example.semiwiki_backend.domain.user.exception.UserNotFoundException;
import com.example.semiwiki_backend.domain.user.repository.UserRepository;
import com.example.semiwiki_backend.domain.user_notice_board.entity.UserNoticeBoard;
import com.example.semiwiki_backend.domain.user_notice_board.repository.UserNoticeBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public UserMyPageResponseDto GetUserInfo(String accountId) {
        User user = userRepository.findByAccountId(accountId).orElseThrow(() -> new UserNotFoundException());
        int noticeBoardcount = userNoticeBoardRepository.countUserNoticeBoardsByUser(user); //게시판과의 관계의 개수를 셈 == 기여한 게시판 수

        return  UserMyPageResponseDto.builder()
                .noticeBoardCount(noticeBoardcount)
                .accountId(user.getAccountId())
                .build();
    }

    //유저가 기여한(제작, 수정) 게시글 목록
    public List<NoticeBoardListResponseDto> GetNoticeBoardsFromUser(String accountId, NoticeBoardListDto dto) {
        final List<String> categories = dto.getCategories();
        final String keyword = dto.getKeyword();
        final int offset = dto.getOffset();
        final int limit = dto.getLimit();

        User user = userRepository.findByAccountId(accountId).orElseThrow(() -> new UserNotFoundException());

        List<NoticeBoard> noticeBoards = new ArrayList<>();
        if(dto.getOrderBy().equals("recent")){
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
        else if(dto.getOrderBy().equals("like")){
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
            UserPreviewResponseDto userPreviewResponseDto = UserPreviewResponseDto.builder()
                    .userId(noticeBoard.getUsers().get(0).getUser().getId()) // 첫 번째 작성자 기준
                    .accountId(noticeBoard.getUsers().get(0).getUser().getAccountId())
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

    public Long GetNoticeBoardCount(String accountId, NoticeBoardCountRequestDto requestDto) {
        final String title = requestDto.getTitle();
        final List<String> categories = requestDto.getCategories();
        if(title == null) {
            if (categories == null)
                return userNoticeBoardRepository.countAllByUser(accountId);

            return userNoticeBoardRepository.countByUserAndCategoriesAllMatch(accountId,categories, categories.size());
        }
        if(categories == null)
            return userNoticeBoardRepository.countByUserAndTitleContainingIgnoreCase(accountId,title);

        return userNoticeBoardRepository.countByUserAndTitleContainingAndCategoriesAllMatch(accountId, title,categories, categories.size());
    }
}

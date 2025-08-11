package com.example.semiwiki_backend.domain.notice_table.service;

import com.example.semiwiki_backend.domain.notice_table.dto.response.NoticeTableDetailResponseDto;
import com.example.semiwiki_backend.domain.notice_table.dto.response.NoticeTableListResponseDto;
import com.example.semiwiki_backend.domain.notice_table.entity.NoticeTable;
import com.example.semiwiki_backend.domain.notice_table.exception.NoticeTableNotFoundException;
import com.example.semiwiki_backend.domain.notice_table.repository.NoticeTableRepository;
import com.example.semiwiki_backend.domain.user.dto.response.UserPreviewResponseDto;
import com.example.semiwiki_backend.domain.user.entity.User;
import com.example.semiwiki_backend.domain.user_notice_table.entity.UserNoticeTable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoticeTableGetService {
    private final NoticeTableRepository noticeTableRepository;

    public NoticeTableGetService(NoticeTableRepository noticeTableRepository) {
        this.noticeTableRepository = noticeTableRepository;
    }

    public NoticeTableDetailResponseDto getNoticeTable(Integer noticeTableId) {
        NoticeTable noticeTable = noticeTableRepository.findById(noticeTableId).orElseThrow(() -> new NoticeTableNotFoundException("id : " + noticeTableId + " 를 찾을수 없습니다."));
        List<User> users = new ArrayList<>();
        List<UserNoticeTable> userNoticeTableList = noticeTable.getUsers();
        for(UserNoticeTable userNoticeTable : userNoticeTableList)
            users.add(userNoticeTable.getUser());

        return NoticeTableDetailResponseDto.builder()
                .title(noticeTable.getTitle())
                .contents(noticeTable.getContents())
                .createdAt(noticeTable.getCreatedAt())
                .modficatedAt(noticeTable.getModficatedAt())
                .users(users)
                .build();
    }

    public List<NoticeTableListResponseDto> getNoticeTableListByCategorys(List<String> categories) {
        List<NoticeTable> noticeTableList = noticeTableRepository.findAllByCategory(categories);
        List<NoticeTableListResponseDto> noticeTableListDto = new ArrayList<>();
        for(NoticeTable noticeTable : noticeTableList) {
            List<User> users = new ArrayList<>();
            List<UserNoticeTable> userNoticeTableList = noticeTable.getUsers();
            for(UserNoticeTable userNoticeTable : userNoticeTableList)
                users.add(userNoticeTable.getUser());
            int usersLength = users.size();
            UserPreviewResponseDto userPreviewResponseDto = UserPreviewResponseDto.builder()
                    .userId(users.get(usersLength-1).getId())
                    .accountId(users.get(usersLength-1).getAccountId())
                    .build();
            noticeTableListDto.add(NoticeTableListResponseDto.builder()
                    .id(noticeTable.getId())
                    .categories(noticeTable.getCategories())
                    .title(noticeTable.getTitle())
                    .userPreview(userPreviewResponseDto)
                    .build());
        }
        return noticeTableListDto;
    }

    public List<NoticeTableListResponseDto> getAllNoticeTables() {
        List<NoticeTable> noticeTableList = noticeTableRepository.findAll();
        List<NoticeTableListResponseDto> noticeTableListDto = new ArrayList<>();
        for(NoticeTable noticeTable : noticeTableList) {
            List<User> users = new ArrayList<>();
            List<UserNoticeTable> userNoticeTableList = noticeTable.getUsers();
            for(UserNoticeTable userNoticeTable : userNoticeTableList)
                users.add(userNoticeTable.getUser());
            int usersLength = users.size();
            UserPreviewResponseDto userPreviewResponseDto = UserPreviewResponseDto.builder()
                            .userId(users.get(usersLength-1).getId())
                            .accountId(users.get(usersLength-1).getAccountId())
                            .build();
            noticeTableListDto.add(NoticeTableListResponseDto.builder()
                            .id(noticeTable.getId())
                            .categories(noticeTable.getCategories())
                            .title(noticeTable.getTitle())
                            .userPreview(userPreviewResponseDto)
                    .build());
        }
        return noticeTableListDto;
    }

    public List<NoticeTableListResponseDto> searchNoticeTables(String keyword) {
        List<NoticeTable> noticeTableList = noticeTableRepository.findByTitleContaining(keyword);
        List<NoticeTableListResponseDto> noticeTableListDto = new ArrayList<>();
        for(NoticeTable noticeTable : noticeTableList) {
            List<User> users = new ArrayList<>();
            List<UserNoticeTable> userNoticeTableList = noticeTable.getUsers();
            for(UserNoticeTable userNoticeTable : userNoticeTableList)
                users.add(userNoticeTable.getUser());
            int usersLength = users.size();
            UserPreviewResponseDto userPreviewResponseDto = UserPreviewResponseDto.builder()
                    .userId(users.get(usersLength-1).getId())
                    .accountId(users.get(usersLength-1).getAccountId())
                    .build();
            noticeTableListDto.add(NoticeTableListResponseDto.builder()
                    .id(noticeTable.getId())
                    .categories(noticeTable.getCategories())
                    .title(noticeTable.getTitle())
                    .userPreview(userPreviewResponseDto)
                    .build());
        }
        return noticeTableListDto;
    }

}

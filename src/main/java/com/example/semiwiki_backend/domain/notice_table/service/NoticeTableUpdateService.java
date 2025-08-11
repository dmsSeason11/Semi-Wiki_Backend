package com.example.semiwiki_backend.domain.notice_table.service;

import com.example.semiwiki_backend.domain.notice_table.dto.request.NoticeTableUpdateRequest;
import com.example.semiwiki_backend.domain.notice_table.dto.response.NoticeTableDetailResponseDto;
import com.example.semiwiki_backend.domain.notice_table.entity.NoticeTable;
import com.example.semiwiki_backend.domain.notice_table.exception.NoticeTableNotFoundException;
import com.example.semiwiki_backend.domain.notice_table.repository.NoticeTableRepository;
import com.example.semiwiki_backend.domain.user.entity.User;
import com.example.semiwiki_backend.domain.user.exception.UserNotFoundException;
import com.example.semiwiki_backend.domain.user.repository.UserRepository;
import com.example.semiwiki_backend.domain.user_notice_table.entity.UserNoticeTable;
import com.example.semiwiki_backend.domain.user_notice_table.repository.UserNoticeTableRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoticeTableUpdateService {
    private final NoticeTableRepository noticeTableRepository;
    private final UserRepository userRepository;
    private final UserNoticeTableRepository userNoticeTableRepository;

    public NoticeTableUpdateService(NoticeTableRepository noticeTableRepository, UserRepository userRepository, UserNoticeTableRepository userNoticeTableRepository) {
        this.noticeTableRepository = noticeTableRepository;
        this.userRepository = userRepository;
        this.userNoticeTableRepository = userNoticeTableRepository;
    }

    @Transactional
    public NoticeTableDetailResponseDto updateNoticeTable(NoticeTableUpdateRequest dto, Integer noticeTableId){
        NoticeTable noticeTable = noticeTableRepository.findById(noticeTableId).orElseThrow(() -> new NoticeTableNotFoundException("id : " + noticeTableId + " 를 찾을수 없습니다."));//noticeTable존재 확인

        noticeTable.setTitle(dto.getTitle());
        noticeTable.setContents(dto.getContents());
        noticeTable.setCategories(dto.getCategories());//noticeTable 설정

        List<UserNoticeTable> userNoticeTableList = noticeTable.getUsers();
        userNoticeTableList.add(userNoticeTableRepository.save(UserNoticeTable.builder()
                .user(userRepository.findById(dto.getUserId()).orElseThrow(() -> new UserNotFoundException("유저를 찾을수 없습니다.")))
                .noticeBoard(noticeTable).build()));
        noticeTable.setUsers(userNoticeTableList);

        List<UserNoticeTable> userNoticeList = noticeTable.getUsers();
        List<User> users = new ArrayList<>();
        for(UserNoticeTable userNotice : userNoticeTableList)
            users.add(userNotice.getUser());

        return NoticeTableDetailResponseDto.builder()
                .title(noticeTable.getTitle())
                .contents(noticeTable.getContents())
                .categories(noticeTable.getCategories())
                .users(users).build();
    }
}

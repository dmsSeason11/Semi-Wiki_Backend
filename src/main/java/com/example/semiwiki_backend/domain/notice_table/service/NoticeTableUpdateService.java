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
<<<<<<< HEAD
    public NoticeTableDetailResponseDto updateNoticeTable(NoticeTableUpdateRequest dto){
        NoticeTable noticeTable = noticeTableRepository.findById(dto.getNoticeTableId())
                .orElseThrow(() -> new NoticeTableNotFoundException("id : " + dto.getNoticeTableId() + " 를 찾을수 없습니다."));

        noticeTable.setTitle(dto.getTitle());
        noticeTable.setContents(dto.getContents());
        noticeTable.setCategories(dto.getCategories());

        List<UserNoticeTable> userNoticeTableList = noticeTable.getUsers();

        User userToAdd = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("유저를 찾을수 없습니다.")); // Users에 추가할 User

        boolean userExists = userNoticeTableList.stream() // user가 있는지 확인
                .anyMatch(unt -> unt.getUser().getId() == userToAdd.getId());

        if (!userExists) { // 없는경우에 실행
            UserNoticeTable newUserNoticeTable = userNoticeTableRepository.save(
                    UserNoticeTable.builder()
                            .user(userToAdd)
                            .noticeBoard(noticeTable)
                            .build()
            );
            userNoticeTableList.add(newUserNoticeTable);
        }

        List<User> users = new ArrayList<>();
        for (UserNoticeTable userNotice : userNoticeTableList)
=======
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
>>>>>>> origin/feat/5-notice-table
            users.add(userNotice.getUser());

        return NoticeTableDetailResponseDto.builder()
                .title(noticeTable.getTitle())
                .contents(noticeTable.getContents())
                .categories(noticeTable.getCategories())
                .users(users).build();
    }
<<<<<<< HEAD

=======
>>>>>>> origin/feat/5-notice-table
}

package com.example.semiwiki_backend.domain.notice_table.service;

import com.example.semiwiki_backend.domain.notice_table.dto.request.NoticeTableCreateRequestDto;
import com.example.semiwiki_backend.domain.notice_table.entity.NoticeTable;
import com.example.semiwiki_backend.domain.notice_table.exception.NoCategoryException;
import com.example.semiwiki_backend.domain.notice_table.repository.NoticeTableRepository;
import com.example.semiwiki_backend.domain.user.exception.UserNotFoundException;
import com.example.semiwiki_backend.domain.user.repository.UserRepository;
import com.example.semiwiki_backend.domain.user_notice_table.entity.UserNoticeTable;
import com.example.semiwiki_backend.domain.user_notice_table.repository.UserNoticeTableRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class NoticeTableCreateService {
    private final NoticeTableRepository noticeTableRepository;

    private final UserRepository userRepository;

    private final UserNoticeTableRepository userNoticeTableRepository;
    public NoticeTableCreateService(NoticeTableRepository noticeTableRepository, UserRepository userRepository, UserNoticeTableRepository userNoticeTableRepository) {
        this.noticeTableRepository = noticeTableRepository;
        this.userRepository = userRepository;
        this.userNoticeTableRepository = userNoticeTableRepository;
    }

    @Transactional
    public NoticeTable createNoticeBoard(NoticeTableCreateRequestDto dto) {
        List<String> categories = dto.getCategories();
        if (categories == null || categories.isEmpty())
            throw new NoCategoryException("카테고리가 없습니다.");
        NoticeTable noticeTable = noticeTableRepository.save(NoticeTable.builder()
                .title(dto.getTitle())
                .contents(dto.getContents())
                .categories(categories)
                .build());
        UserNoticeTable userNoticeTable = UserNoticeTable.builder()
                .user(userRepository.findById(dto.getUserId()).orElseThrow(() -> new UserNotFoundException("유저를 찾지 못했습니다.")))
                .noticeBoard(noticeTable).build();
        noticeTable.addUserNotice(userNoticeTable);
        userNoticeTableRepository.save(userNoticeTable);
        return noticeTable;
    }
}

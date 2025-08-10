package com.example.semiwiki_backend.domain.user_notice_table.repository;

import com.example.semiwiki_backend.domain.user_notice_table.entity.UserNoticeTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserNoticeTableRepository extends JpaRepository<UserNoticeTable,Integer> {
}

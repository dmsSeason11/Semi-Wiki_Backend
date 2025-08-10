package com.example.semiwiki_backend.domain.notice_table.repository;

import com.example.semiwiki_backend.domain.notice_table.entity.NoticeTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeTableRepository extends JpaRepository<NoticeTable, Integer> {
}

package com.example.semiwiki_backend.domain.user_notice_table.repository;

import com.example.semiwiki_backend.domain.user_notice_table.entity.UserNoticeTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserNoticeTableRepository extends JpaRepository<UserNoticeTable,Integer> {
    public List<UserNoticeTable> findAllByUser_Id(int userId);
}

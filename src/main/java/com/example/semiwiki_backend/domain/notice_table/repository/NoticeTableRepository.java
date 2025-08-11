package com.example.semiwiki_backend.domain.notice_table.repository;

import com.example.semiwiki_backend.domain.notice_table.entity.NoticeTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NoticeTableRepository extends JpaRepository<NoticeTable, Integer> {
    @Query("SELECT DISTINCT n FROM NoticeTable n JOIN n.categories c WHERE c IN :categories")
    public List<NoticeTable> findAllByCategory(@Param("categories") List<String> categories);

    public List<NoticeTable> findByTitleContaining(String keyword);
}

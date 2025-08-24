package com.example.semiwiki_backend.domain.notice_board.repository;

import com.example.semiwiki_backend.domain.notice_board.entity.NoticeBoardHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeBoardHeaderRepository extends JpaRepository<NoticeBoardHeader,Long> {
}

package com.example.semiwiki_backend.domain.user.repository;

import com.example.semiwiki_backend.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByAccountId(String accountId);
    boolean existsByAccountId(String accountId);
    boolean existsByStudentNum(int studentNum);
}

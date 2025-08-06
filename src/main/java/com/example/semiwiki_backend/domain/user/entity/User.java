package com.example.semiwiki_backend.domain.user.entity;

import com.example.semiwiki_backend.domain.user_notice_table.Entity.UserNoticeTable;
import com.example.semiwiki_backend.global.security.auth.Role;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String accountId;

    @Column(nullable = false, unique = true)
    private int studentNum;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<UserNoticeTable> noticeTables;


}

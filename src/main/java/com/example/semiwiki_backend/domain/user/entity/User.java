package com.example.semiwiki_backend.domain.user.entity;

import com.example.semiwiki_backend.domain.user_notice_table.Entity.UserNoticeTable;
import jakarta.persistence.*;

import java.util.List;

@Entity
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

    @OneToMany(mappedBy = "user")
    private List<UserNoticeTable> noticeTables;


}

package com.example.semiwiki_backend.domain.user.entity;

import com.example.semiwiki_backend.domain.user_notice_table.entity.UserNoticeTable;
import com.example.semiwiki_backend.global.security.auth.Role;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
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
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user")
    private List<UserNoticeTable> noticeTables;

    @Builder
    public User(String accountId, int studentNum, String username, Role role, String password) {
        this.accountId = accountId;
        this.studentNum = studentNum;
        this.username = username;
        this.role = role;
        this.password = password;
    }


}

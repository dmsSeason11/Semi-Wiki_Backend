package com.example.semiwiki_backend.domain.user.entity;

import com.example.semiwiki_backend.domain.user_notice_table.entity.UserNoticeTable;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true,name = "account_id")
    private String accountId;

    @Column(nullable = false, unique = true, name = "student_num")
    private int studentNum;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user")
    private List<UserNoticeTable> noticeTables;


}

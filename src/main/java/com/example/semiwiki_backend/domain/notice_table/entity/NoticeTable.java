package com.example.semiwiki_backend.domain.notice_table.entity;

import com.example.semiwiki_backend.domain.user_notice_table.entity.UserNoticeTable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class NoticeTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "modificated_at")
    private LocalDateTime modficatedAt;

    @OneToMany
    private List<UserNoticeTable> users;

    @ElementCollection
    @CollectionTable(name = "NoticeTableCategory", joinColumns = @JoinColumn(name = "notice_table_id"))
    @Column(name = "category")
    private List<String> categories;
}

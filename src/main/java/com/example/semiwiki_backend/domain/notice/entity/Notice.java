package com.example.semiwiki_backend.domain.notice.entity;

import com.example.semiwiki_backend.domain.notice.type.Type;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 5000)
    private String contents;

    @Enumerated(EnumType.STRING)
    private Type type;

    public void updateNotice(String title, String contents, Type type) {
        if(title != null)
            this.title = title;
        if(contents != null)
            this.contents = contents;
        if(type != null)
            this.type = type;
    }
}
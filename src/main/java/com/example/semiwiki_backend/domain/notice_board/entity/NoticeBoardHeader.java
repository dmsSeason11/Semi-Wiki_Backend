package com.example.semiwiki_backend.domain.notice_board.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoticeBoardHeader {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, name = "header_number")
    private String headerNumber;

    @Column(nullable = false)
    private Integer number;

    @Column(nullable = false)
    private Integer level;

    @Column(nullable = false)
    private String contents;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<NoticeBoardHeader> children = new ArrayList<>();

    public void updateHeaderContents(String contents, String title){
        this.contents = contents;
        this.title = title;
    }

    public void addHeader(NoticeBoardHeader header){
        this.children.add(header);
    }

    public void setContentsInGenerate(String contents){
        this.contents = contents;
    }
}

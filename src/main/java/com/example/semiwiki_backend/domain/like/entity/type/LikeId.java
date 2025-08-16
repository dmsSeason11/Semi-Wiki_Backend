package com.example.semiwiki_backend.domain.like.entity.type;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class LikeId implements Serializable {
    private Integer user;
    private Integer noticeBoard;
}

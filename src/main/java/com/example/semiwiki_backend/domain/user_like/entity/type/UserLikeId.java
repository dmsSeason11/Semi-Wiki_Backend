package com.example.semiwiki_backend.domain.user_like.entity.type;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserLikeId implements Serializable {
    private Integer user;
    private Integer noticeBoard;
}

package com.example.semiwiki_backend.domain.user_like.entity.type;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserLikeId implements Serializable {
    private Integer user;
    private Integer noticeBoard;
}

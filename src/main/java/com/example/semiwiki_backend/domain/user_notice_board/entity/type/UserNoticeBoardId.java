package com.example.semiwiki_backend.domain.user_notice_board.entity.type;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class UserNoticeBoardId implements Serializable {
    private Integer user;
    private Integer noticeBoard;
}

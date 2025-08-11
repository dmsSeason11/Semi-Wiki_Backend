package com.example.semiwiki_backend.domain.user_notice_board.entity.type;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class UserNoticeBoardId implements Serializable {
    private Integer user;
    private Integer noticeBoard;
}

package com.example.semiwiki_backend.domain.user_notice_table.entity.type;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class UserNoticeTableId implements Serializable {
    private Integer userId;
    private Integer noticeBoardId;
}

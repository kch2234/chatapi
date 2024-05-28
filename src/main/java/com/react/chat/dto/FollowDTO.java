package com.react.chat.dto;

import com.react.chat.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FollowDTO {
    private Long id;
    private Member follower;
    private Member follow;
    private LocalDateTime createDate;

}

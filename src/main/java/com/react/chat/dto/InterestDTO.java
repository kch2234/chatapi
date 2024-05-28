package com.react.chat.dto;

import com.react.chat.domain.enumFiles.Interests;
import com.react.chat.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InterestDTO {
    private Long id;
    private Member member;
    private Interests interest;

}

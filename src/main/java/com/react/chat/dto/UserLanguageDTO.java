package com.react.chat.dto;

import com.react.chat.domain.enumFiles.UserLanguages;
import com.react.chat.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLanguageDTO {
    private Long id;
    private Member memberId;
    private UserLanguages userLanguages;
}

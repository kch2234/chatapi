package com.react.chat.dto;

import com.react.chat.domain.member.Member;
import lombok.Data;

@Data
public class ProfileImageDTO {
    private Long id;
    private String fileName;
    private int ord;
    private Member memberProfile;
}

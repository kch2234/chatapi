package com.react.chat.dto;

import com.react.chat.domain.member.Member;
import lombok.Data;

@Data
public class ProfileImageDTO {
    private Long id;
    private String fileName;
    private int ord;
    private Member member;

    // 생성자 Entity -> DTO
    public ProfileImageDTO(Long id, String fileName, int ord, Member member) {
        this.id = id;
        this.fileName = fileName;
        this.ord = ord;
        this.member = member;
    }


}

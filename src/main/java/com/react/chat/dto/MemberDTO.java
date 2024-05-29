package com.react.chat.dto;

import com.react.chat.domain.enumFiles.Gendar;
import com.react.chat.domain.enumFiles.Role;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class MemberDTO {
    private Long id;
    private String email;
    private String nickname;
    private String password;
    private List<ProfileImageDTO> profileImageDTO;
    private int phone;
    private String introduction;
    private LocalDateTime birth;
    private boolean disabled;
    private LocalDateTime disabledDate;
    private String nationality;
    private Gendar gendar;
    private Role role;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    // 생성자
    public MemberDTO(Long id, String email, String nickname, String password, int phone, String introduction, LocalDateTime birth, boolean disabled, LocalDateTime disabledDate, String nationality, Gendar gendar, Role role) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.phone = phone;
        this.introduction = introduction;
        this.birth = birth;
        this.disabled = disabled;
        this.disabledDate = disabledDate;
    }

}

package com.react.chat.dto;

import com.react.chat.domain.enumFiles.Gender;
import com.react.chat.domain.enumFiles.Role;
import com.react.chat.domain.member.Member;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class MemberDTO {
    private Long id;
    private String email;
    private String nickname;
    private String password;
    private List<ProfileImageDTO> profileImageDTO;
    private String phone;
    private String introduction;
    private LocalDateTime birth;
    private boolean disabled;
    private LocalDateTime disabledDate;
    private String nationality;
    private Gender gender;
    private Role role;
//    private String status;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    // 생성자
    public MemberDTO(Long id, String email, String nickname, String password, String phone, String introduction, LocalDateTime birth, boolean disabled, LocalDateTime disabledDate, String nationality, Gender gender, Role role) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.phone = phone;
        this.introduction = introduction;
        this.birth = birth;
        this.disabled = disabled;
        this.disabledDate = disabledDate;
        this.nationality = nationality;
        this.gender = gender;
        this.role = role;
    }

    // 현재 사용자 정보를 Map 타입으로 리턴 : JWT 를 위한 메서드 -> 추후 JWT 문자열 생성시 사용
    // MemberDTO -> Map<String,Object> 타입으로 변환해서 리턴
    public Map<String, Object> getClaims() {
        Map<String, Object> map = new HashMap<>();
        map.put("email", email);
        map.put("password", password); // 비번은 나중에 전달 안하는 것으로 변경. 지금은 확인차 추가
        map.put("nickname", nickname);
        map.put("phone", phone);
        map.put("introduction", introduction);
        map.put("birth", birth);
        map.put("disabled", disabled);
        map.put("disabledDate", disabledDate);
        map.put("nationality", nationality);
        map.put("gendar", gender);
        map.put("role", role);
        return map;
    }
}

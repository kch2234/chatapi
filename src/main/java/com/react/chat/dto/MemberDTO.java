package com.react.chat.dto;

import com.react.chat.domain.enumFiles.Gender;
import com.react.chat.domain.enumFiles.Role;
import lombok.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class MemberDTO extends User {
  private Long id;
  private String email;
  private String password;
  private Role role;

  // 생성자 Entity -> DTO
  public MemberDTO (Long id, String email, String password, Role role) {
    super(email, password, Collections.singletonList(new SimpleGrantedAuthority(role.name())));

    this.id = id;
    this.email = email;
    this.password = password;
    this.role = role;
  }

  // 현재 사용자 정보를 Map 타입으로 리턴 : JWT 를 위한 메서드 -> 추후 JWT 문자열 생성시 사용
  // MemberDTO -> Map<String,Object> 타입으로 변환해서 리턴
  public Map<String, Object> getClaims() {
    Map<String, Object> map = new HashMap<>();
    map.put("id", id);
    map.put("email", email);
    map.put("password", password);
    map.put("role", role);
    return map;
  }

}


//  public MemberDTO(Member member) {
//    super(member.getEmail(), member.getPassword(), Collections.singletonList(new SimpleGrantedAuthority(member.getRole().name())));
//    this.id = member.getId();
//    this.email = member.getEmail();
//    this.nickname = member.getNickname();
//    this.password = member.getPassword();
//    this.phone = member.getPhone();
//    this.introduction = member.getIntroduction();
//    this.birth = member.getBirth();
//    this.nationality = member.getNationality();
//    this.gender = member.getGender();
//    this.role = member.getRole();
//    this.disabled = member.isDisabled();
//    this.disabledDate = member.getDisabledDate();
//    this.createDate = member.getCreateDate();
//    this.updateDate = member.getUpdateDate();
//
//  }

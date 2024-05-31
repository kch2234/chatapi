package com.react.chat.dto;

import com.react.chat.domain.enumFiles.Gender;
import com.react.chat.domain.enumFiles.Role;
import lombok.Setter;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;
import java.util.List;

public class MemberDTO extends User {
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
  private LocalDateTime createDate;
  private LocalDateTime updateDate;

  // 생성자 Entity -> DTO
  public MemberDTO (Long id, String email, String nickname, String password, List<ProfileImageDTO> profileImageDTO, String phone, String introduction, LocalDateTime birth, boolean disabled, LocalDateTime disabledDate, String nationality, Gender gender, Role role, LocalDateTime createDate, LocalDateTime updateDate) {
    super(email, password, null);

    this.id = id;
    this.email = email;
    this.nickname = nickname;
    this.password = password;
    this.profileImageDTO = profileImageDTO;
    this.phone = phone;
    this.introduction = introduction;
    this.birth = birth;
    this.nationality = nationality;
    this.gender = gender;
    this.role = role;
    this.disabled = disabled;
    this.disabledDate = disabledDate;
    this.createDate = createDate;
    this.updateDate = updateDate;
  }

}

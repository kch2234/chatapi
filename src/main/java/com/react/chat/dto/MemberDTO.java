package com.react.chat.dto;

import com.react.chat.domain.enumFiles.Gender;
import com.react.chat.domain.enumFiles.Role;
import com.react.chat.domain.member.Member;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Setter
public class MemberDTO extends User {
  private Long id;
  private String email;
  private String nickname;
  private String password;
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


  // 생성자 Entity -> DTO
  public MemberDTO (Long id, String email, String nickname, String password,
                    String phone, String introduction, LocalDateTime birth,
                    boolean disabled, LocalDateTime disabledDate,
                    String nationality, Gender gender, Role role,
                    LocalDateTime createDate, LocalDateTime updateDate) {
    super(email, password, Collections.singletonList(new SimpleGrantedAuthority(role.name())));

    this.id = id;
    this.email = email;
    this.nickname = nickname;
    this.password = password;
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

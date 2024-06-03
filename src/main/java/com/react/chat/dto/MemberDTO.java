package com.react.chat.dto;

import com.react.chat.domain.enumFiles.Gender;
import com.react.chat.domain.enumFiles.Role;
import com.react.chat.domain.member.Member;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;
import java.util.*;

@Setter
public class MemberDTO extends User {
  private Long id;
  private String email;
  private String nickname;
  private String password;
  private List<ProfileImageDTO> profileImageDTO = new ArrayList<>();
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
  public MemberDTO (Long id, String email, String nickname, String password, List<ProfileImageDTO> profileImageDTO,
                    String phone, String introduction, LocalDateTime birth,
                    boolean disabled, LocalDateTime disabledDate,
                    String nationality, Gender gender, Role role,
                    LocalDateTime createDate, LocalDateTime updateDate) {
    super(email, password, Collections.singletonList(new SimpleGrantedAuthority(role.name())));

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

  // 현재 사용자 정보를 Map 타입으로 리턴 : JWT 를 위한 메서드 -> 추후 JWT 문자열 생성시 사용
  // MemberDTO -> Map<String,Object> 타입으로 변환해서 리턴
  public Map<String, Object> getClaims() {
    Map<String, Object> map = new HashMap<>();
    map.put("id", id);
    map.put("email", email);
    map.put("password", password); // 비번은 나중에 전달 안하는 것으로 변경. 지금은 확인차 추가
    map.put("profileImageDTO", profileImageDTO); // List<ProfileImageDTO> 타입
    map.put("phone", phone);
    map.put("nickname", nickname);
    map.put("introduction", introduction);
    map.put("birth", birth.toString());
    map.put("nationality", nationality);
    map.put("gender", gender);
    map.put("role", role);
    map.put("disabled", disabled);
    map.put("disabledDate", disabledDate);
    map.put("createDate", createDate);
    map.put("updateDate", updateDate);

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

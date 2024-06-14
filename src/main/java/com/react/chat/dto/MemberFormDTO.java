package com.react.chat.dto;

import com.react.chat.domain.enumFiles.Gender;
import com.react.chat.domain.enumFiles.Role;
import com.react.chat.domain.enumFiles.UserLanguages;
import com.react.chat.util.JsonUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Builder
@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class MemberFormDTO {
  private Long id;
  private String email;
  private String password;
  private Role role;
  private String nickname;
  private String phone;
  private String introduction;
  private LocalDate birth;
  private String nationality;
  private List<String> languageList = new ArrayList<>();
  private Gender gender;


  @Builder.Default
  private List<MultipartFile> files = new ArrayList<>();  // 업로드 예정 파일 리스트

  @Builder.Default
  private List<String> uploadedFileNames = new ArrayList<>();  // 업로드 완료 파일 네임 리스트

  /*public MemberFormDTO (Long id, String email, String nickname, String password, List<String> uploadedFileNames,
                    String phone, String introduction, LocalDate birth,
                    boolean disabled, LocalDateTime disabledDate,
                    String nationality, Gender gender, Role role,
                    LocalDateTime createDate, LocalDateTime updateDate) {

    this.id = id;
    this.email = email;
    this.password = password;
    this.role = role;
    this.nickname = nickname;
    this.uploadedFileNames = uploadedFileNames;
    this.phone = phone;
    this.introduction = introduction;
    this.birth = birth;
    this.nationality = nationality;
    this.gender = gender;
    this.disabled = disabled;
    this.disabledDate = disabledDate;
    this.createDate = createDate;
    this.updateDate = updateDate;
  }*/

  // 현재 사용자 정보를 Map 타입으로 리턴 : JWT 를 위한 메서드 -> 추후 JWT 문자열 생성시 사용
  // MemberDTO -> Map<String,Object> 타입으로 변환해서 리턴
  public Map<String, Object> getClaims() {
    Map<String, Object> map = new HashMap<>();
    map.put("id", id);
    map.put("email", email);
    map.put("password", password);  // 추후 삭제
    map.put("role", role);
    map.put("uploadedFileNames", uploadedFileNames); // List<ProfileImageDTO> 타입
    map.put("phone", phone);
    map.put("nickname", nickname);
    map.put("introduction", introduction);
    map.put("birth", birth.toString());
    map.put("nationality", nationality);
    map.put("languageList", languageList); // List<String> 타입
    map.put("gender", gender);
//    map.put("disabled", disabled);
//    map.put("disabledDate", disabledDate);
//    map.put("createDate", createDate);
//    map.put("updateDate", updateDate);

    return map;
  }
}

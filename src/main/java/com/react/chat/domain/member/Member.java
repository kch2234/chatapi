package com.react.chat.domain.member;

import com.react.chat.domain.baseEntity.BaseEntityUpdatedDate;
import com.react.chat.domain.chatting.ChatRoom;
import com.react.chat.domain.chatting.ConversationRequest;
import com.react.chat.domain.enumFiles.Gender;
import com.react.chat.domain.enumFiles.Role;
import com.react.chat.domain.enumFiles.UserLanguages;
import com.react.chat.dto.MemberDTO;
import com.react.chat.dto.ProfileImageDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member extends BaseEntityUpdatedDate {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 회원 번호
    @Column(nullable = false, unique = true)
    private String email; // 이메일
    @Column(nullable = false, unique = true)
    private String nickname; // 닉네임
    @Column(nullable = false, length = 500)
    private String password; // 비밀번호

    @ElementCollection
    @Builder.Default
    //@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProfileImage> imageList = new ArrayList<>();//프로필 이미지

    private String phone; // 전화번호

    @Column(length = 1000)
    private String introduction; // 자기 소개

    @Column(nullable = false)
    private LocalDate birth; // 생년월일

    @Column(nullable = false)
    private String nationality; // 국적

    @ElementCollection
    @Builder.Default
    @Enumerated(EnumType.STRING) // enum 문자열로 들어가도록
    private List<UserLanguages> languageList = new ArrayList<>();

    @Enumerated(EnumType.STRING) // enum 문자열로 들어가도록
    @Column(nullable = false, updatable = false)
    private Gender gender; // 성별

    @Enumerated(EnumType.STRING) // enum 문자열로 들어가도록
    @Column(nullable = false, updatable = false)
    private Role role; // 권한

    // TODO : 랜덤매칭 기능 추가시 사용
    //private String status; // 상태

    @Builder.Default
    private boolean disabled = false; // 탈퇴 여부 기본값 false : 탈퇴시 true
    private LocalDateTime disabledDate; // 탈퇴 날짜 : 30일 후 DB 삭제

    @ManyToMany(mappedBy = "members")
    @Builder.Default
    private List<ChatRoom> chatRooms = new ArrayList<>(); // 채팅방

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ConversationRequest> sentRequests = new ArrayList<>(); // 보낸 대화 요청

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ConversationRequest> receivedRequests = new ArrayList<>(); // 받은 대화 요청

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Interest> interests = new ArrayList<>(); // 관심사

    // 필드 수정 메서드
    // 권한 추가
    public void addRole(Role role) { this.role = role; }
    // 사용 언어 추가 (setter)
    public void addUserLanguages(List<UserLanguages> userLanguages) { this.languageList = userLanguages; }
    // 닉네임 수정
    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }
    // 비밀번호 수정
    public void changePassword(String password) {
        this.password = password;
    }
    // 자기 소개 수정
    public void changeIntroduction(String introduction) {
        this.introduction = introduction;
    }
    // 탈퇴 여부 수정
    public void changeDisabled(boolean disabled) {
        this.disabled = disabled;
    }
    // 탈퇴 날짜 수정
    public void changeDisabledDate(LocalDateTime disabledDate) {
        this.disabledDate = disabledDate;
    }
    // 국적 수정
    public void changeNationality(String nationality) {
        this.nationality = nationality;
    }

    // ProfileImage 타입으로 이미지 추가
    public void addImage(ProfileImage image) {
        image.setOrd(this.imageList.size());
        imageList.add(image);
    }
    // 문자열로 이미지 파일 추가
    public void addImageString(String fileName) {
        ProfileImage productImage = ProfileImage.builder()
            .fileName(fileName)
            .build();
        addImage(productImage);
    }

}
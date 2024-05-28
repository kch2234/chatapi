package com.react.chat.domain.member;

import com.react.chat.domain.baseEntity.BaseEntityCreatedDate;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileImage extends BaseEntityCreatedDate {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // 프로필 사진 이름
    private String profileImageName;

}

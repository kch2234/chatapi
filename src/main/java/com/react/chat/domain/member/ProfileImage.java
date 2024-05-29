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

    @Column(nullable = false)
    private String fileName;

    @Setter // 편의를 위해 ord만 setter 걸어주기
    @Column(nullable = false)
    private int ord;  // 이미지마다 번호 지정, 대표이미지 = 0 인것

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

}

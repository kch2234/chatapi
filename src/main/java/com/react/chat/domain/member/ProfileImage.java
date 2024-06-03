package com.react.chat.domain.member;

import com.react.chat.domain.baseEntity.BaseEntityCreatedDate;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
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

    private String fileName;
    private int ord;  // 이미지마다 번호 지정, 대표이미지 = 0 인것

}

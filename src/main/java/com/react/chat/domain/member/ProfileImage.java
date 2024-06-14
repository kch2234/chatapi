package com.react.chat.domain.member;

import jakarta.persistence.*;
import lombok.*;

//@Entity
@Embeddable
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileImage {
    //@Id
    //@GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    //private Long id;

    //@ManyToOne
    //@JoinColumn(name = "member_id", nullable = false)
    //private Member member;
    private String fileName;
    private int ord;  // 이미지마다 번호 지정, 대표이미지 = 0 인것

}

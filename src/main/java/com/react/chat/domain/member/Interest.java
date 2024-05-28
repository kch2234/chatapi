package com.react.chat.domain.member;

import com.react.chat.domain.baseEntity.BaseEntityCreatedDate;
import com.react.chat.domain.enumFiles.Interests;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Interest extends BaseEntityCreatedDate {
    @Id @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    private Interests interest;

}

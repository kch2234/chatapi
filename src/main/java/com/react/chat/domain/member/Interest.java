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
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private Interests interest;

}

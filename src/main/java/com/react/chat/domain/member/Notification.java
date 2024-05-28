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
public class Notification extends BaseEntityCreatedDate {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    private String type; // follow, ConversationRequest : 팔로우, 대화 요청
    private Long refernceId; // 참조 ID
    private boolean readFlag = false; // 읽음 여부 (기본값: false)

}

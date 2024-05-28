package com.react.chat.domain.chatting;

import com.react.chat.domain.baseEntity.BaseEntityUpdatedDate;
import com.react.chat.domain.member.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConversationRequest extends BaseEntityUpdatedDate {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "senderId", nullable = false)
    private Member senderId;

    @ManyToOne
    @JoinColumn(name = "receiverId", nullable = false)
    private Member receiver;
    // 수락, 거절 여부 (기본값: false) - 대화 신청시 확인 여부
    private boolean acceptFlag = false;

}

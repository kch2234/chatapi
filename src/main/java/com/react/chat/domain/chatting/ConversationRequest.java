package com.react.chat.domain.chatting;

import com.react.chat.domain.baseEntity.BaseEntityUpdatedDate;
import com.react.chat.domain.enumFiles.ChattingStatus;
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
    private Member sender;

    @ManyToOne
    @JoinColumn(name = "receiverId", nullable = false)
    private Member receiver;
    // 수락, 대기, 거절 여부
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private ChattingStatus status = ChattingStatus.WAIT;

    // 필드 수정 메서드
    // status
    public void updateStatus(ChattingStatus status) {
        this.status = status;
    }
}

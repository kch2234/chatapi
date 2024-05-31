package com.react.chat.domain.chatting;

import com.react.chat.domain.baseEntity.BaseEntityUpdatedDate;
import com.react.chat.domain.member.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage extends BaseEntityUpdatedDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatRoomId", nullable = false)
    private ChatRoom chatRoom;

    @ManyToOne
    @JoinColumn(name = "senderId", nullable = false)
    private Member member;

    private String message;

    // 메시지 전송 시간
    private LocalDateTime timestamp;

}

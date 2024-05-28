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
public class ChatMessage extends BaseEntityUpdatedDate {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chatRoomId", nullable = false)
    private ChatRoom chatRoom;

    @ManyToOne
    @JoinColumn(name = "senderId", nullable = false)
    private Member senderId;

    private String message;

}

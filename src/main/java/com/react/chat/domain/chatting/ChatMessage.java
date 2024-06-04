package com.react.chat.domain.chatting;

import com.react.chat.domain.baseEntity.BaseEntityCreatedDate;
import com.react.chat.domain.enumFiles.MessageType;
import com.react.chat.domain.member.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage extends BaseEntityCreatedDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;
    private LocalDateTime timestamp;

//    private MessageType messageType;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member sender;

    @Setter
    @ManyToOne
    @JoinColumn(name = "chatroom_id", nullable = false)
    private ChatRoom chatRoom;

}

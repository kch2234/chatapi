package com.react.chat.domain.chatting;

import com.react.chat.domain.baseEntity.BaseEntityCreatedDate;
import com.react.chat.domain.enumFiles.MessageType;
import com.react.chat.domain.member.Member;
import com.react.chat.dto.ChatMessageDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

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

    @Enumerated(EnumType.STRING)
    private MessageType messageType;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member sender;

    @ManyToOne
    @JoinColumn(name = "chatroom_id", nullable = false)
    private ChatRoom chatRoom;

    @Column(nullable = false)
    private LocalTime timestamp = LocalTime.now();

    public ChatMessageDTO toDTO() {
        return ChatMessageDTO.builder()
                .id(id)
                .messageType(messageType)
                .content(content)
                .sender(sender)
                .roomId(chatRoom.getId())
                .timestamp(timestamp)
                .build();
    }
}

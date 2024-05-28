package com.react.chat.domain.chatting;


import com.react.chat.domain.baseEntity.BaseEntityCreatedDate;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoom extends BaseEntityCreatedDate {
    @Id @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "chatRoom")//cascade = CascadeType.REMOVE) => 삭제시 채팅방 삭제
    private List<ChatMessage> chatMessageList;

    /*@Column(nullable = false)
    private String roomName; // 채팅방 이름*/
}

package com.react.chat.domain.chatting;


import com.react.chat.domain.baseEntity.BaseEntityUpdatedDate;
import com.react.chat.domain.member.Member;
import com.react.chat.dto.ChatRoomDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoom extends BaseEntityUpdatedDate {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    @Setter
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Member sender;
    @Setter
    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private Member receiver;

    /*@OneToMany(mappedBy = "chatRoom")//cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ChatMessage> chatMessageList = new ArrayList<>();*/

}

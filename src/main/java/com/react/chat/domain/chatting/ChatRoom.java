package com.react.chat.domain.chatting;


import com.react.chat.domain.baseEntity.BaseEntityUpdatedDate;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoom extends BaseEntityUpdatedDate {
    @Id @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "chatRoom")//cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatMessage> chatMessageList = new ArrayList<>();
}

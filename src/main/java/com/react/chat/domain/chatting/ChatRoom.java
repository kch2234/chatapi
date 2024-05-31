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
    private Long id;
    private String roomName;

/*    @ManyToMany
    private Set<Member> chatMembers = new HashSet<>();*/


    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
    @ManyToOne
    @JoinColumn(name = "tmember_id", nullable = false)
    private Member toMember;

    @OneToMany(mappedBy = "chatRoom")//cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ChatMessage> chatMessageList = new ArrayList<>();

/*    public void setMember(Long id) {
        this.member = Member.builder().id(id).build();
    }*/

/*    public ChatRoomDTO toDTO() {
        return ChatRoomDTO.builder()
                .id(id)
                .roomName(roomName)
                .member(member)
                .toMember(toMember)
                .chatMessageList(chatMessageList)
                .build();
    }*/

    public void changeRoomName(String roomName) {
        this.roomName = roomName;
    }
}

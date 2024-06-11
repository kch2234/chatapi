package com.react.chat.domain.chatting;

import com.react.chat.domain.baseEntity.BaseEntityCreatedDate;
import com.react.chat.domain.member.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom extends BaseEntityCreatedDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(
            name = "chatroom_members",
            joinColumns = @JoinColumn(name = "chatroom_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id")
    )
    @Builder.Default
    private Set<Member> members = new HashSet<>();

    @Setter
    private String lastMessage;
    @Setter
    private LocalDateTime lastMessageTime;

    // 필드 수정 메서드 추가
}


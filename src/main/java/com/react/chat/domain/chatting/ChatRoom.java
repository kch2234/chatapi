package com.react.chat.domain.chatting;

import com.react.chat.domain.baseEntity.BaseEntityCreatedDate;
import com.react.chat.domain.member.Member;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.socket.WebSocketSession;

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

    // 세션 조회
    @Getter
    @Transient // 데이터베이스에 저장되지 않도록 Transient 어노테이션 사용
    private Set<WebSocketSession> sessions = new HashSet<>();

    // 세션 추가
    public void addSession(WebSocketSession session) {
        sessions.add(session);
    }

    // 세션 제거
    public void removeSession(WebSocketSession session) {
        sessions.remove(session);
    }

    public void setId(Long id) {
        this.id = id;
    }
}


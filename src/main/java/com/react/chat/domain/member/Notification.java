package com.react.chat.domain.member;

import com.react.chat.domain.baseEntity.BaseEntityCreatedDate;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Notification extends BaseEntityCreatedDate {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    private String type; // 알림 타입 follow, ConversationRequest : 팔로우, 대화 요청
    private Long refernceId; // 참조 ID
    private boolean readFlag = false; // 읽음 여부 (기본값: false)

}
/*
@Entity
@Getter
@Setter
public class Notify extends Auditable {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "notification_id")
   private Long id;

   private String content;
private String url;
@Column(nullable = false)
private Boolean isRead;

@Enumerated(EnumType.STRING)
@Column(nullable = false)
private NotificationType notificationType;

@ManyToOne
@JoinColumn(name = "member_id")
@OnDelete(action = OnDeleteAction.CASCADE)
private Member receiver;

@Builder
public Notify(Member receiver, NotificationType notificationType, String content, String url, Boolean isRead) {
    this.receiver = receiver;
    this.notificationType = notificationType;
    this.content = content;
    this.url = url;
    this.isRead = isRead;
}

public Notify() {

}


public enum NotificationType{
    YATA, REVIEW, CHAT
}
}
*/
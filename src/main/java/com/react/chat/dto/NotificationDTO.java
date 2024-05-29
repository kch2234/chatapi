package com.react.chat.dto;

import com.react.chat.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.internal.LoadingCache;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {
    private Long id;
    private String type;
    private Long refernceId;
    private boolean readFlag;
    private Member member;
}

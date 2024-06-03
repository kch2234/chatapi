package com.react.chat.domain.enumFiles;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    // 사용자 권한
//    USER, ADMIN;
    USER("ROLE_USER"), ADMIN("ROLE_ADMIN");
    private final String value;
}

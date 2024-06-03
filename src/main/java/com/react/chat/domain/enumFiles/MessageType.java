package com.react.chat.domain.enumFiles;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MessageType {
    ENTER("ENTER"), JOIN("JOIN"), TALK("TALK"), EXIT("EXIT"), SUB("SUBSCRIBE"), PUB("PUBLISH");
    private final String type;
}

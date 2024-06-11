package com.react.chat.repository;

import com.react.chat.domain.chatting.ConversationRequest;
import com.react.chat.domain.enumFiles.ChattingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConversationRequestRepository extends JpaRepository<ConversationRequest, Long>{
    List<ConversationRequest> findByIdAndStatus(Long recipientId, ChattingStatus status);
}

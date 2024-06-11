package com.react.chat.service;

import com.react.chat.domain.chatting.ChatRoom;
import com.react.chat.domain.chatting.ConversationRequest;
import com.react.chat.domain.enumFiles.ChattingStatus;
import com.react.chat.domain.member.Member;
import com.react.chat.dto.ChatRoomDTO;
import com.react.chat.dto.ConversationRequestDTO;
import com.react.chat.repository.ChatRoomRepository;
import com.react.chat.repository.ConversationRequestRepository;
import com.react.chat.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConversationRequestService {

    private final ConversationRequestRepository conversationRequestRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    public List<ConversationRequestDTO> getPendingRequests(Long recipientId) {
        List<ConversationRequest> requests = conversationRequestRepository.findByIdAndStatus(recipientId, ChattingStatus.WAIT);
        return requests.stream()
                .map(request -> modelMapper.map(request, ConversationRequestDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public ConversationRequestDTO requestChat(Long senderId, Long recipientId) {
        Member findSender = memberRepository.findById(senderId).orElseThrow(() -> new IllegalArgumentException("Invalid sender ID"));
        Member findRecipient = memberRepository.findById(recipientId).orElseThrow(() -> new IllegalArgumentException("Invalid recipient ID"));

        ConversationRequest conversationRequest = ConversationRequest.builder()
                .sender(findSender)
                .receiver(findRecipient)
                .status(ChattingStatus.WAIT)
                .build();

        ConversationRequest savedRequest = conversationRequestRepository.save(conversationRequest);
        return modelMapper.map(savedRequest, ConversationRequestDTO.class);
    }

    @Transactional
    public ChatRoomDTO acceptChatRequest(Long requestId) {
        ConversationRequest conversationRequest = conversationRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid request ID"));

        conversationRequest.updateStatus(ChattingStatus.ACCEPT);
        conversationRequestRepository.save(conversationRequest);

        ChatRoom chatRoom = ChatRoom.builder()
                .sender(conversationRequest.getSender())
                .receiver(conversationRequest.getReceiver())
                .build();

        ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);
        return modelMapper.map(savedChatRoom, ChatRoomDTO.class);
    }

    @Transactional
    public ConversationRequestDTO declineChatRequest(Long requestId) {
        ConversationRequest conversationRequest = conversationRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid request ID"));

        conversationRequest.updateStatus(ChattingStatus.REJECT);
        ConversationRequest savedRequest = conversationRequestRepository.save(conversationRequest);
        return modelMapper.map(savedRequest, ConversationRequestDTO.class);
    }
}
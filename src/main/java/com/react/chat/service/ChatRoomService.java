package com.react.chat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.react.chat.domain.chatting.ChatRoom;
import com.react.chat.domain.member.Member;
import com.react.chat.dto.ChatMessageDTO;
import com.react.chat.dto.ChatRoomDTO;
import com.react.chat.dto.MemberDTO;
import com.react.chat.repository.ChatRoomRepository;
import com.react.chat.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ModelMapper modelMapper;
    private final MemberRepository memberRepository;

    // 채팅방 생성
    @Transactional
    public Long createChatRoom(ChatRoomDTO chatRoomDTO) {
        ChatRoom chatRoom = modelMapper.map(chatRoomDTO, ChatRoom.class);
        ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);
        return savedChatRoom.getId();
    }

    public ChatRoom findRoomById(Long roomId) {
        return chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("채팅방이 존재하지 않습니다."));
    }

    // 특정 채팅방 조회
    public ChatRoomDTO getChatRoom(Long chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new IllegalArgumentException("채팅방이 존재하지 않습니다."));
        return modelMapper.map(chatRoom, ChatRoomDTO.class);
    }

    // 사용자가 채팅한 모든 채팅방 조회
    public List<ChatRoomDTO> getChatRoomsByMemberEmail(String email) {
        log.info("******** ChatRoomService getChatRoomsByMemberEmail - email : {}", email);
        Long findMember = memberRepository.findByEmail(email).getId();
        log.info("******** ChatRoomService getChatRoomsByMemberEmail - findMember : {}", findMember);
        List<ChatRoom> chatRooms = chatRoomRepository.findByMembersEmail(findMember);
        log.info("******** ChatRoomService getChatRoomsByMemberEmail - chatRooms : {}", chatRooms);
        return chatRooms.stream()
                .map(chatRoom -> modelMapper.map(chatRoom, ChatRoomDTO.class))
                .collect(Collectors.toList());
    }

    // 채팅방 삭제
    @Transactional
    public void deleteChatRoom(Long chatRoomId) {
        chatRoomRepository.deleteById(chatRoomId);
    }

    public void addSessionToChatRoom(Long chatRoomId, WebSocketSession session) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new IllegalArgumentException("채팅방이 존재하지 않습니다."));
        chatRoom.addSession(session);
    }

    public void removeSessionFromChatRoom(Long chatRoomId, WebSocketSession session) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new IllegalArgumentException("채팅방이 존재하지 않습니다."));
        chatRoom.removeSession(session);
    }

    public void sendMessageToChatRoom(Long chatRoomId, ChatMessageDTO messageDTO) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new IllegalArgumentException("채팅방이 존재하지 않습니다."));
        Set<WebSocketSession> sessions = chatRoom.getSessions();
        sessions.forEach(session -> {
            try {
                session.sendMessage(new TextMessage(new ObjectMapper().writeValueAsString(messageDTO)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}

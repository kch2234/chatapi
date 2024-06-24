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
    public ChatRoomDTO createChatRoom(String email, Long memberId) {
        // 이메일로 로그인한 회원 조회
        Member loginMember = memberRepository.findByEmail(email);

        // memberId로 회원 조회
        Member sender = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("ID가 " + memberId + "인 회원을 찾을 수 없습니다"));

        // 새로운 채팅방 생성
        ChatRoom chatRoom = ChatRoom.builder()
                .name(sender.getNickname()) // 다른 회원의 닉네임을 이름으로 설정
                .build();

        // 두 회원을 채팅방에 추가
        chatRoom.getMembers().add(loginMember);
        chatRoom.getMembers().add(sender);

        // 데이터베이스에 채팅방 저장
        chatRoomRepository.save(chatRoom);

        // DTO로 변환하여 반환
        return new ChatRoomDTO(chatRoom.getId(), chatRoom.getName());
    }

    public ChatRoom findRoomById(Long roomId) {
        return chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("채팅방이 존재하지 않습니다."));
    }

/*    // 특정 채팅방 조회
    public ChatRoomDTO getChatRoom(Long chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new IllegalArgumentException("채팅방이 존재하지 않습니다."));
        return modelMapper.map(chatRoom, ChatRoomDTO.class);
    }*/

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

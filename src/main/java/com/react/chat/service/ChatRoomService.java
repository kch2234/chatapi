package com.react.chat.service;

import com.react.chat.domain.chatting.ChatRoom;
import com.react.chat.domain.member.Member;
import com.react.chat.dto.ChatRoomDTO;
import com.react.chat.repository.ChatRoomRepository;
import com.react.chat.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public ChatRoomDTO createChatRoom(Long senderId, Long recipientId) {
        Member sender = memberRepository.findById(senderId).orElseThrow(() -> new IllegalArgumentException("Invalid sender ID"));
        Member recipient = memberRepository.findById(recipientId).orElseThrow(() -> new IllegalArgumentException("Invalid recipient ID"));

        ChatRoom chatRoom = ChatRoom.builder()
                .name(sender.getNickname() + " & " + recipient.getNickname() + "'s Chat Room")
                .members(Set.of(sender, recipient))
                .build();

        ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);
        return modelMapper.map(savedChatRoom, ChatRoomDTO.class);
    }

    @Transactional
    public ChatRoomDTO addMemberToChatRoom(Long chatRoomId, Long memberId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(() -> new IllegalArgumentException("Invalid chat room ID"));
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));

        chatRoom.getMembers().add(member);
        chatRoomRepository.save(chatRoom);

        return modelMapper.map(chatRoom, ChatRoomDTO.class);
    }

    @Transactional
    public void updateLastMessage(Long chatRoomId, String message) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(() -> new IllegalArgumentException("Invalid chat room ID"));

        chatRoom.setLastMessage(message);
        chatRoom.setLastMessageTime(LocalDateTime.now());
        chatRoomRepository.save(chatRoom);
    }
}

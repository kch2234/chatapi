package com.react.chat.service;

import com.react.chat.domain.chatting.ChatRoom;
import com.react.chat.domain.member.Interest;
import com.react.chat.domain.member.Member;
import com.react.chat.dto.ChatRoomDTO;
import com.react.chat.repository.ChatRoomRepository;
import com.react.chat.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MemberRepository memberRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ModelMapper modelMapper;

    // 매칭된 사용자 찾기
    public Optional<ChatRoomDTO> findMatch(Long memberId) {
        return Optional.empty();
    }

    // 채팅방 생성
    public ChatRoomDTO createChatRoom(Long senderId, Long recipientId) {
        Member sender = memberRepository.findById(senderId).orElseThrow(() -> new IllegalArgumentException("Invalid sender ID"));
        Member recipient = memberRepository.findById(recipientId).orElseThrow(() -> new IllegalArgumentException("Invalid recipient ID"));

        ChatRoom chatRoom = ChatRoom.builder()
                .name(sender.getNickname() + " & " + recipient.getNickname() + "'s Chat Room")
                .members(new HashSet<>(List.of(sender, recipient)))
                .build();

        ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);
        return modelMapper.map(savedChatRoom, ChatRoomDTO.class);
    }
}

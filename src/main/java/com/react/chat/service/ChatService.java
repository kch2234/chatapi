package com.react.chat.service;

import com.react.chat.domain.chatting.ChatMessage;
import com.react.chat.domain.chatting.ChatRoom;
import com.react.chat.domain.member.Member;
import com.react.chat.dto.ChatMessageDTO;
import com.react.chat.dto.ChatRoomDTO;
import com.react.chat.repository.ChatMessageRepository;
import com.react.chat.repository.ChatRoomRepository;
import com.react.chat.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ChatService {
    private final MemberRepository memberRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final ModelMapper modelMapper;

    // 채팅방 생성
    @Transactional
    public Long add(ChatRoomDTO chatRoomDTO) {
        ChatRoom chatRoom = modelMapper.map(chatRoomDTO, ChatRoom.class);
        ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);
        return savedChatRoom.getId();
    }

    // 채팅방 조회
    public ChatRoomDTO get(Long id) {
        ChatRoom findChatRoom = chatRoomRepository.findById(id).orElseThrow();
        ChatRoomDTO dto = modelMapper.map(findChatRoom, ChatRoomDTO.class);
        return dto;
    }

    // 채팅방 이름 수정
    @Transactional
    public void modify(ChatRoomDTO chatRoomDTO) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomDTO.getId()).orElseThrow();
        chatRoom.changeRoomName(chatRoomDTO.getRoomName());
    }

    // 채팅방 삭제
    @Transactional
    public void remove(Long id) {
        ChatRoom findChatRoom = chatRoomRepository.findById(id).orElse(null);
        if (findChatRoom != null) {
            chatRoomRepository.delete(findChatRoom);
        } else {
            throw new IllegalArgumentException("해당 채팅방이 존재하지 않습니다.");
        }
    }

    // 채팅방 목록조회 - 작성순, List - ASC
    public List<ChatRoomDTO> getListASC() {
        List<ChatRoomDTO> list = chatRoomRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))
                .stream().map(chatRoom -> modelMapper.map(chatRoom, ChatRoomDTO.class))
                .collect(Collectors.toList());
        return list;
    }

    // 채팅방 목록조회 - 최신순, List - DESC
    public List<ChatRoomDTO> getListDESC() {
        List<ChatRoomDTO> list = chatRoomRepository.findAll(Sort.by(Sort.Direction.DESC, "id"))
                .stream().map(chatRoom -> modelMapper.map(chatRoom, ChatRoomDTO.class))
                .collect(Collectors.toList());
        return list;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // 채팅방 메시지 전송
}

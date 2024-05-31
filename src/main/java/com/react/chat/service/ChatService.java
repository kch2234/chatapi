package com.react.chat.service;

import com.react.chat.domain.chatting.ChatRoom;
import com.react.chat.dto.ChatRoomDTO;
import com.react.chat.repository.ChatRoomRepository;
import com.react.chat.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {
    private final ChatRoomRepository chatRoomRepository;
    private final ModelMapper modelMapper;

    // 채팅방 생성
    @Transactional
    public Long add(ChatRoomDTO chatRoomDTO) {
        ChatRoom chatRoom = modelMapper.map(chatRoomDTO, ChatRoom.class);
        ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);
        return savedChatRoom.getId();
    }

    // 채팅방 조회
    @Transactional
    public ChatRoomDTO get(Long id) {
        ChatRoom chatRoom = chatRoomRepository.findById(id).orElseThrow();
        ChatRoomDTO dto = modelMapper.map(chatRoom, ChatRoomDTO.class);
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
        ChatRoom findChatRoom = chatRoomRepository.findById(id).orElseThrow();
        if (findChatRoom != null) {
            chatRoomRepository.delete(findChatRoom);
        }else {
            log.info("해당 채팅방이 존재하지 않습니다.");
            log.info("삭제 실패.....");
        }
    }

    // 채팅방 목록조회 - 작성순, List - ASC
    public List<ChatRoomDTO> getListASC() {
        List<ChatRoom> list = chatRoomRepository.findAll(Sort.by(Sort.Direction.ASC, "createdDate"));
        return list.stream().map(chatRoom -> modelMapper
                .map(chatRoom, ChatRoomDTO.class))
                .collect(Collectors.toList());
    }

    // 채팅방 목록조회 - 최신순, List - DESC

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // ChatRoomName 검색목록조회 - 작성순, List - ASC

    // ChatRoomName 검색목록조회 - 최신순, List - DESC

}

package com.react.chat.service;

import com.react.chat.domain.chatting.ChatRoom;
import com.react.chat.dto.ChatRoomDTO;
import com.react.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
/*
    *//** ChatRoom 조회 *//*
    @Transactional
    public ChatRoomDTO findById(final Long id) {
        ChatRoom entity = this.chatRoomRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 ChatRoom이 존재하지 않습니다. id = " + id));
        return new ChatRoomDTO(entity);
    }

    *//** ChatRoom 생성 *//*
    @Transactional
    public Long save(final ChatRoomDTO requestDto) {
        return this.chatRoomRepository.save(requestDto.toEntity()).getId();
    }

    *//** ChatRoom 수정 *//*
    @Transactional
    public Long update(final Long id, ChatRoomDTO requestDto) {
        ChatRoom entity = this.chatRoomRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 ChatRoom이 존재하지 않습니다. id = " + id));
        return entity.update(requestDto);
    }

    *//** ChatRoom 삭제 *//*
    public void delete(final Long id) {
        ChatRoom entity = this.chatRoomRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 ChatRoom이 존재하지 않습니다. id = " + id));
        this.chatRoomRepository.delete(entity);
    }

    *//** ChatRoom 목록조회 - 작성순, List *//*
    @Transactional
    public List<ChatRoomDTO> findAllAsc() {
        List<ChatRoom> chatRoomList = chatRoomRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        List<ChatRoomDTO> roomDTOList = chatRoomList.stream().map(chatRoom -> new ChatRoomDTO(chatRoom))
                .collect(Collectors.toList());
        return roomDTOList;
    }

    *//** ChatRoom 목록조회 - 최신순, List *//*
    @Transactional
    public List<ChatRoomDTO> findALlDesc() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        List<ChatRoom> chatRoomList = this.chatRoomRepository.findAll(sort);
        return chatRoomList.stream().map(ChatRoomDTO::new).collect(Collectors.toList());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    *//** ChatRoom 검색목록조회 - 작성순, List *//*
    @Transactional
    public List<ChatRoomDTO> findAllByRoomNameAsc(String roomName) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        List<ChatRoom> chatRoomList = this.chatRoomRepository.findAllByRoomNameContaining(roomName, sort);
        return chatRoomList.stream().map(ChatRoomDTO::new).collect(Collectors.toList());
    }

    *//** ChatRoom 검색목록조회 - 최신순, List *//*
    @Transactional
    public List<ChatRoomDTO> findAllByRoomNameDesc(String roomName) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        List<ChatRoom> chatRoomList = this.chatRoomRepository.findAllByRoomNameContaining(roomName, sort);
        return chatRoomList.stream().map(ChatRoomDTO::new).collect(Collectors.toList());
    }*/

}

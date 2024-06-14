package com.react.chat.service;

import com.react.chat.domain.chatting.ChatMessage;
import com.react.chat.domain.chatting.ChatRoom;
import com.react.chat.domain.enumFiles.MessageType;
import com.react.chat.domain.member.Member;
import com.react.chat.dto.ChatMessageDTO;
import com.react.chat.repository.ChatMessageRepository;
import com.react.chat.repository.ChatRoomRepository;
import com.react.chat.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;

    private final SimpMessageSendingOperations messagingTemplate;
    private final SimpMessagingTemplate template;
    private final ModelMapper modelMapper;

    // 메시지 목록 조회
    public List<ChatMessageDTO> getMessagesByChatRoomId(Long chatRoomId) {
        Optional<ChatRoom> getRoomId = chatRoomRepository.findById(chatRoomId);
        if (getRoomId.isPresent()) {
            ChatRoom chatRoom = getRoomId.get();
            List<ChatMessage> findMessageList = chatMessageRepository.findByChatRoom(chatRoom);
            return findMessageList.stream()
                    .map(message -> modelMapper.map(message, ChatMessageDTO.class))
                    .collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException("존재하지 않는 채팅방입니다.");
        }
    }

    // 메시지 저장
    public ChatMessageDTO saveMessage(ChatMessageDTO messageDTO) {
        Optional<Member> findMember = memberRepository.findById(messageDTO.getSender().getId());
        Optional<ChatRoom> findRoom = chatRoomRepository.findById(messageDTO.getChatRoom().getId());
        if (findMember.isPresent() && findRoom.isPresent()) {
            Member sender = findMember.get();
            ChatMessage message = modelMapper.map(messageDTO, ChatMessage.class);
            message.setSender(sender);
            message.setChatRoom(findRoom.get());
            message.setTimestamp(LocalDateTime.now());
            ChatMessage savedMessage = chatMessageRepository.save(message);
            return modelMapper.map(savedMessage, ChatMessageDTO.class);
        } else {
            throw new IllegalArgumentException("존재하지 않는 회원이거나 채팅방입니다.");
        }
    }

    // 메시지 전송
    @Transactional
    public ChatMessageDTO sendMessage(ChatMessageDTO messageDTO) {
        Optional<Member> findMember = memberRepository.findById(messageDTO.getSender().getId());
        log.info("findMember: {}", findMember);
        Optional<ChatRoom> findRoom = chatRoomRepository.findById(messageDTO.getChatRoom().getId());
        log.info("findRoom: {}", findRoom);
        if (findMember.isPresent() && findRoom.isPresent()) {
            Member sender = findMember.get();
            ChatRoom chatRoom = findRoom.get();

            ChatMessage message = ChatMessageDTO.builder()
                    .MessageType(MessageType.MESSAGE)
                    .content(messageDTO.getContent())
                    .sender(sender)
                    .ChatRoom(chatRoom)
                    .timestamp(LocalDateTime.now())
                    .build().toEntity();

            ChatMessage savedMessage = chatMessageRepository.save(message);
            log.info("Message saved to DB: {}", savedMessage);

            ChatMessageDTO savedMessageDTO = modelMapper.map(savedMessage, ChatMessageDTO.class);
            template.convertAndSend("/sub/chat/room/" + messageDTO.getChatRoom().getId(), savedMessageDTO);

            return savedMessageDTO;
        } else {
            throw new IllegalArgumentException("존재하지 않는 회원이거나 채팅방입니다.");
        }
    }



    @Transactional
    public ChatMessageDTO addUser(ChatMessageDTO messageDTO) {
        Optional<Member> findMember = memberRepository.findById(messageDTO.getSender().getId());
        Optional<ChatRoom> findRoom = chatRoomRepository.findById(messageDTO.getChatRoom().getId());
        if (findMember.isPresent() && findRoom.isPresent()) {
            Member sender = findMember.get();
            messageDTO.setContent(sender.getNickname() + "님이 입장하셨습니다.");
            ChatMessage message = modelMapper.map(messageDTO, ChatMessage.class);
            message.setSender(sender);
            message.setChatRoom(findRoom.get());
            message.setTimestamp(LocalDateTime.now());
            message.setMessageType(MessageType.ENTER);
            chatMessageRepository.save(message);
            ChatMessageDTO savedMessageDTO = modelMapper.map(message, ChatMessageDTO.class);
            template.convertAndSend("/sub/chat/room/" + messageDTO.getChatRoom().getId(), savedMessageDTO);
            return savedMessageDTO;
        } else {
            throw new IllegalArgumentException("존재하지 않는 회원이거나 채팅방입니다.");
        }
    }

    @Transactional
    public ChatMessageDTO leaveUser(ChatMessageDTO messageDTO) {
        Optional<Member> findMember = memberRepository.findById(messageDTO.getSender().getId());
        Optional<ChatRoom> findRoom = chatRoomRepository.findById(messageDTO.getChatRoom().getId());
        if (findMember.isPresent() && findRoom.isPresent()) {
            Member sender = findMember.get();
            messageDTO.setContent(sender.getNickname() + "님이 퇴장하셨습니다.");
            ChatMessage message = modelMapper.map(messageDTO, ChatMessage.class);
            message.setSender(sender);
            message.setChatRoom(findRoom.get());
            message.setTimestamp(LocalDateTime.now());
            message.setMessageType(MessageType.LEAVE);
            chatMessageRepository.save(message);
            ChatMessageDTO savedMessageDTO = modelMapper.map(message, ChatMessageDTO.class);
            template.convertAndSend("/sub/chat/room/" + messageDTO.getChatRoom().getId(), savedMessageDTO);
            return savedMessageDTO;
        } else {
            throw new IllegalArgumentException("존재하지 않는 회원이거나 채팅방입니다.");
        }
    }
}

package com.react.chat.service;

import com.react.chat.domain.chatting.ChatRoom;
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
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));

        List<Member> potentialMatches = memberRepository.findByMatching(
                /*member.getInterest(),*/null, member.getGender(), member.getNationality());

        if (potentialMatches.isEmpty()) {
            return Optional.empty();
        }

        Member match = potentialMatches.get(0);  // 첫 번째 일치하는 사용자를 선택
        ChatRoom chatRoom = ChatRoom.builder()
                .name(member.getNickname() + " & " + match.getNickname() + "'s Chat Room")
                .members(new HashSet<>(List.of(member, match)))
                .build();

        ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);
        return Optional.of(modelMapper.map(savedChatRoom, ChatRoomDTO.class));
    }
}

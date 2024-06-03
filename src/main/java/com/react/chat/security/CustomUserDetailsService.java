package com.react.chat.security;

import com.react.chat.domain.member.Member;
import com.react.chat.dto.MemberDTO;
import com.react.chat.dto.ProfileImageDTO;
import com.react.chat.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final MemberRepository memberRepository;
  private final ModelMapper modelMapper;

  // email 로 회원 조회 -> MemberDTO(UserDetails 타입)으로 변환 후 리턴
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    log.info("***** CustomUserDetailsService/loadUserByUsername - username : {}", username);

    Member member = memberRepository.getMemberByEmail(username);
    if (member == null) { // 없는 사용자(email)일 경우 예외 발생
      throw new UsernameNotFoundException("Email(username) Not Found");
    }

    MemberDTO memberDTO = new MemberDTO(member.getId(), member.getEmail(), member.getNickname(), member.getPassword(),
        member.getPhone(), member.getIntroduction(), member.getBirth(),
        member.isDisabled(), member.getDisabledDate(),
        member.getNationality(), member.getGender(), member.getRole(),
        member.getCreateDate(), member.getUpdateDate());


      log.info("***** CustomUserDetailsService/loadUserByUsername - memberDTO : {}", memberDTO);

      return memberDTO;



  }
}

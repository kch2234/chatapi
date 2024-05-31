package com.react.chat.security;

import com.react.chat.domain.member.Member;
import com.react.chat.domain.member.ProfileImage;
import com.react.chat.dto.MemberDTO;
import com.react.chat.dto.ProfileImageDTO;
import com.react.chat.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service @Slf4j @RequiredArgsConstructor public class CustomUserDetailsService implements UserDetailsService {

  private final MemberRepository memberRepository;
  private final ModelMapper modelMapper;

  // email 로 회원 조회 -> MemberDTO(UserDetails 타입)으로 변환 후 리턴
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // 매개변수 username (시큐리티 명칭) == email (직접 만든 Member 변수명)
    log.info("***** CustomUserDetailsService/loadUserByUsername - username : {}", username);

    Member member = memberRepository.getMemberByEmail(username);
    if (member == null) { // 없는 사용자(email)일 경우 예외 발생
      throw new UsernameNotFoundException("Email(username) Not Found");
    }

    modelMapper.addMappings(new PropertyMap<ProfileImageDTO, ProfileImage>() {
      @Override
      protected void configure() {
        map().setId(source.getId());
        map().setMember(source.getMember());
        map().setFileName(source.getFileName());
        map().setOrd(source.getOrd());
      }
    });
    modelMapper.map(profileImageDTO, ProfileImage.class);

    MemberDTO memberDTO = modelMapper.map(member, MemberDTO.class);

    log.info("***** CustomUserDetailsService/loadUserByUsername - memberDTO : {}", memberDTO);

    return memberDTO;

    }
}
    //    MemberDTO memberDTO = MemberDTO.builder()
//        .id(member.getId())
//        .email(member.getEmail())
//        .nickname(member.getNickname())
//        .password(member.getPassword())
//        .profileImageDTO(member.getImageList() != null ? member.getImageList()
//            .stream()
//            .map(profileImage -> new ProfileImageDTO(profileImage.getId(), profileImage.getFileName(), profileImage.getOrd(), profileImage.getMember()))
//            .collect(Collectors.toList()) : Collections.emptyList())
//        .phone(member.getPhone())
//        .introduction(member.getIntroduction())
//        .birth(member.getBirth())
//        .disabled(member.isDisabled())
//        .disabledDate(member.getDisabledDate())
//        .nationality(member.getNationality())
//        .gender(member.getGender())
//        .role(member.getRole())
//        .createDate(member.getCreateDate())
//        .updateDate(member.getUpdatedDate())
//        .build();

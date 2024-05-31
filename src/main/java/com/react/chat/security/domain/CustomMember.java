package com.react.chat.security.domain;

import com.react.chat.domain.member.Member;
import com.react.chat.dto.MemberDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Arrays;
import java.util.Collection;

public class CustomMember extends User {

  private MemberDTO memberDTO;

  public CustomMember(String username, String password, Collection<? extends GrantedAuthority> authorities) {
    super(username, password, authorities);
  }

//  public CustomMember(Member member) {
//    super(member.getEmail(), member.getPassword(),
//        Arrays.asList(new SimpleGrantedAuthority(member.getRole().getValue())));
//    this.memberDTO = new MemberDTO(member);
//  }

}

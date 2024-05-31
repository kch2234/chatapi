package com.react.chat.repository;

import com.react.chat.domain.enumFiles.Gender;
import com.react.chat.domain.enumFiles.Role;
import com.react.chat.domain.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTest {

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Test
  public void testInsert() {
    for (int i = 0; i < 10; i++) {
      Member member = Member.builder()
          .email("user" + i + "@test.com")
          .password(passwordEncoder.encode("1111"))
          .nickname("User" + i)
          .birth(LocalDateTime.of(2024, 5, 31, 17, 23))
          .nationality("한국")
          .gender(Gender.MALE)
          .role(Role.USER)
          .build();
      memberRepository.save(member); // 저장
    }// for
  }

}
package com.react.chat.repository;

import com.react.chat.domain.enumFiles.Gender;
import com.react.chat.domain.enumFiles.Role;
import com.react.chat.domain.member.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@Transactional // EntityManager 사용위해
@Rollback(value = false) // 테스트에서는 자동롤백이 되므로, DB에 수정결과 유지하기 위해 롤백안되게 설정
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
          .password(passwordEncoder.encode("1234"))
          .nickname("User" + i)
          .birth(LocalDateTime.of(2024, 5, 31, 17, 23))
          .nationality("한국")
          .gender(Gender.MALE)
          .role(Role.USER)
          .build();
      memberRepository.save(member); // 저장
    }// for
  }

    // 회원 한개 조회
    @Test
    public void testRead() {
        String email = "user9@test.com";
        Member findMember = memberRepository.getMemberByEmail(email);
        log.info("********** member : {}", findMember);
    }
}
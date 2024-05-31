package com.react.chat.repository;

import com.react.chat.domain.enumFiles.Gender;
import com.react.chat.domain.enumFiles.Role;
import com.react.chat.domain.member.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@SpringBootTest
@Slf4j
@Transactional // EntityManager 사용위해
@Rollback(value = false) // 테스트에서는 자동롤백이 되므로, DB에 수정결과 유지하기 위해 롤백안되게 설정
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    // 회원 추가
    @Test
    public void testAdd() {
        for (int i = 1; i <= 11; i++) {
            Member member = Member.builder()
                    .email("user" + i + "@test.com")
                    .nickname("User" + i)
                    .password("1234")
                    .phone("010-1111-1111")
                    .birth(LocalDateTime.of(2000, 12, 30, 0, 0))
                    .nationality("KOR")
                    .gender(Gender.MALE)
                    .role(Role.USER)
                    .build();
            memberRepository.save(member);
        }
    }

    // 회원 한개 조회
    @Test
    public void testRead() {
        String email = "user9@test.com";
        Member findMember = memberRepository.findByEmail(email);
        log.info("********** member : {}", findMember);
    }
}
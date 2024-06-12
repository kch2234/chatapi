package com.react.chat.controller;

import com.react.chat.dto.MemberDTO;
import com.react.chat.dto.MemberFormDTO;
import com.react.chat.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class LoginController {

  private final MemberService memberService;

  // 회원가입
  @PostMapping("/signup")
  public ResponseEntity<Long> signup(MemberFormDTO memberFormDTO) {
    Long memberId = memberService.signup(memberFormDTO);
    return ResponseEntity.ok(memberId);
  }

}

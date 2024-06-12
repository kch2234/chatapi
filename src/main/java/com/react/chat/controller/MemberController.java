package com.react.chat.controller;

import com.react.chat.domain.member.Member;
import com.react.chat.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

  private final MemberService memberService;

  @PostMapping("/checkEmail")
  public ResponseEntity<Boolean> checkEmail(String email) {
    ResponseEntity<Boolean> res = null;
    try{
      Boolean result = memberService.checkEmail(email);
      //            Boolean result2 = !result1;
      log.info("***** MemberController /checkEmail - 중복 결과 : {}", result);
      res = new ResponseEntity<Boolean>(result, HttpStatus.OK);

    } catch (Exception e){
      res = new ResponseEntity<Boolean>(HttpStatus.BAD_REQUEST);
    }
    return res;
  }

}

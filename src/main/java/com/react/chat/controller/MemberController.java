package com.react.chat.controller;

import com.react.chat.domain.member.Member;
import com.react.chat.dto.MemberDTO;
import com.react.chat.dto.MemberFormDTO;
import com.react.chat.dto.PageRequestDTO;
import com.react.chat.dto.PageResponseDTO;
import com.react.chat.service.MemberService;
import com.react.chat.util.FileUtilCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.util.Arrays;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

  private final MemberService memberService;
  private final FileUtilCustom fileUtil;

  @GetMapping("/view/{fileName}")
  public ResponseEntity<Resource> viewFile(@PathVariable("fileName") String fileName) {
    return fileUtil.getFile(fileName);
  }

  @PostMapping("/checkEmail")
  public ResponseEntity<Boolean> checkEmail(@RequestBody MemberFormDTO memberFormDTO) {

    log.info("***** MemberController /checkEmail - memberFormDTO : {}", memberFormDTO);
    log.info("***** MemberController /checkEmail - email : {}", memberFormDTO.getEmail());

    String email = memberFormDTO.getEmail();
    ResponseEntity<Boolean> res = null;
    try {
      Boolean result = memberService.checkEmail(email);
      log.info("***** MemberController /checkEmail - 중복 결과 : {}", result);
      res = new ResponseEntity<Boolean>(result, HttpStatus.OK);

    } catch (Exception e) {
      res = new ResponseEntity<Boolean>(HttpStatus.BAD_REQUEST);
    }
    return res;
  }

  @PostMapping("/checkNickname")
  public ResponseEntity<Boolean> checkNickname(@RequestBody MemberFormDTO memberFormDTO) {

    log.info("***** MemberController /checkEmail - nickname : {}", memberFormDTO.getNickname());

    String nickname = memberFormDTO.getNickname();
    ResponseEntity<Boolean> res = null;
    try {
      Boolean result = memberService.checkNickname(nickname);
      log.info("***** MemberController /checkNickname - 중복 결과 : {}", result);
      res = new ResponseEntity<Boolean>(result, HttpStatus.OK);

    } catch (Exception e) {
      res = new ResponseEntity<Boolean>(HttpStatus.BAD_REQUEST);
    }
    return res;
  }

  @GetMapping("/list")
  public PageResponseDTO<MemberFormDTO> getList(PageRequestDTO pageRequestDTO) {
    log.info("*********** MemberController GET /list - pageRequestDTO : {}", pageRequestDTO);
    return memberService.getList(pageRequestDTO);
  }

  // 회원 목록 조회
    @GetMapping("/members")
    public List<MemberFormDTO> getMembers() {
      List<MemberFormDTO> members = memberService.getAllMembers();
      log.info("******** MemberController GET /members - members : {}", members);
      return members;
    }

}

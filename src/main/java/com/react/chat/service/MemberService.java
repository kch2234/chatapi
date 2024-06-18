package com.react.chat.service;

import com.react.chat.dto.MemberDTO;
import com.react.chat.dto.MemberFormDTO;

import java.util.List;

public interface MemberService {
  Long signup(MemberFormDTO memberFormDTO);
  Boolean checkEmail(String email);
  Boolean checkNickname(String nickname);

  List<MemberFormDTO> getAllMembers();
}

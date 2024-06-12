package com.react.chat.service;

import com.react.chat.dto.MemberDTO;
import com.react.chat.dto.MemberFormDTO;

public interface MemberService {
  Long signup(MemberFormDTO memberFormDTO);
  Boolean checkEmail(String email);
}

package com.react.chat.service;

import com.react.chat.dto.MemberDTO;
import com.react.chat.dto.MemberFormDTO;
import com.react.chat.dto.PageRequestDTO;
import com.react.chat.dto.PageResponseDTO;

public interface MemberService {
  Long signup(MemberFormDTO memberFormDTO);
  Boolean checkEmail(String email);
  Boolean checkNickname(String nickname);
  PageResponseDTO<MemberFormDTO> getList(PageRequestDTO pageRequestDTO);
}

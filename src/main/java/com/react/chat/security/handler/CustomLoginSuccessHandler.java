package com.react.chat.security.handler;

import com.google.gson.Gson;
import com.react.chat.dto.MemberDTO;
import com.react.chat.util.JWTUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

// 로그인 성공시 실행할 클래스
@Slf4j
@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {
  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

    log.info("***** CustomLoginSuccessHandler");

    // * 로그인 성공 -> JSON 문자열로 응답해줄 데이터 생성 -> 응답 *
    // 응답 데이터 생성 -> 사용자 정보
    MemberDTO memberDTO = (MemberDTO) authentication.getPrincipal();
    Map<String, Object> claims = memberDTO.getClaims(); // 사용자정보 Map 타입으로 변환

    // * JWT 토큰 생성
    String accessToken = JWTUtil.generateToken(claims, 10); // 10분
    String refreshToken = JWTUtil.generateToken(claims, 60 * 24); // 24시간
    claims.put("accessToken", accessToken);
    claims.put("refreshToken", refreshToken);

    Gson gson = new Gson();
    String jsonStr = gson.toJson(claims);

    // 응답 (응답 메세지 보내기)
    response.setContentType("application/json; charset=UTF-8");
    PrintWriter writer = response.getWriter();
    writer.println(jsonStr);
    writer.close();
  }
}
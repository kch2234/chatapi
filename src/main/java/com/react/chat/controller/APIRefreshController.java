package com.react.chat.controller;

import com.react.chat.util.CustomJWTException;
import com.react.chat.util.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;
import java.util.Map;

@RestController
@Slf4j
public class APIRefreshController {
  @RequestMapping("/api/member/refresh")
  public Map<String, Object> refresh(@RequestHeader("Authorization") String authHeader, String refreshToken){

    // 헤더 Authorization -> AccessToken
    // 파라미터 -> RefreshToken
    log.info("***** APIRefreshController - authHeader : {}", authHeader);
    log.info("***** APIRefreshController - refreshToken : {}", refreshToken);

    // refesh token 이 없는 경우
    if(refreshToken == null) {
      throw new CustomJWTException("NULL_REFRESH_TOKEN");
    }
    // 헤더값이 맞지 않을 경우
    if(authHeader == null || authHeader.length() < 7 ) {
      throw new CustomJWTException("INVALID_STRING");
    }

    String accessToken = authHeader.substring(7);
    // Access Token 이 만료되지 않은 경우
    if(checkExpiredToken(accessToken) == false){
      return Map.of("accessToken", accessToken, "refreshToken", refreshToken);
    }

    // 이하 Access Token 만료 시점
    // Refresh Token 검증 -> claims 받기
    Map<String, Object> claims = JWTUtil.validateToken(refreshToken);
    log.info("************ APIRefreshController - claims : {}", claims);

    // 새 토큰 생성해 전달
    String newAccessToken = JWTUtil.generateToken(claims, 10);
    String newRefreshToken = checkRemainTime((Integer)claims.get("exp"))
        ? JWTUtil.generateToken(claims, 60 * 24)  // refresh 토큰 유효시간 1시간 미만 시 새로 생성
        : refreshToken;
    return Map.of("accessToken", newAccessToken, "refreshToken", newRefreshToken);
  }

  // Refresh 토큰 유효시간 체크
  private boolean checkRemainTime(Integer exp) {
    Date expDate = new Date((long) exp * 1000);
    long diff = expDate.getTime() - System.currentTimeMillis();
    long diffMin = diff / (1000 * 60);
    return diffMin < 60; // 1시간 미만 true, 1시간 이상 false
  }

  // 토큰 만료 여부 확인 메서드 : 만료 true, 만료X false
  private boolean checkExpiredToken(String accessToken) {
    try {
      // 문제 발생 시 CustomJWTException 예외
      JWTUtil.validateToken(accessToken);
    }catch (CustomJWTException e) {
      // 유효기간 경과 Expired
      if(e.getMessage().equals("Expired")) {
        return true;
      }
    }
    return false;
  }
}
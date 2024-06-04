package com.react.chat.util;

// JWT 부분에서 예외 발생하면 예외 정보를 담아 예외를 일으켜줄 예외 클래스 직접 구현
public class CustomJWTException extends RuntimeException {

  // 예외 클래스 생성자
  public CustomJWTException(String msg) {
    super(msg);  // 예외 발생 시 메세지 설정

  }
}
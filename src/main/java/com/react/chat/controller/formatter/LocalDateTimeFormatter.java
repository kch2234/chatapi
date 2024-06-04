package com.react.chat.controller.formatter;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LocalDateTimeFormatter implements Formatter<LocalDateTime> {

  // 문자열날짜시간 -> LocalDateTime
  @Override
  public LocalDateTime parse(String text, Locale locale) throws ParseException {
    return LocalDateTime.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
  }

  // LocalDateTime -> 문자열날짜시간
  @Override
  public String print(LocalDateTime object, Locale locale) {
    return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(object);
  }

}

package com.react.chat.controller.formatter;

import org.springframework.format.Formatter;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LocalDateFormatter implements Formatter<LocalDate> {

  // 문자열날짜 -> LocalDate
  @Override
  public LocalDate parse(String text, Locale locale) throws ParseException {
    return LocalDate.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
  }

  // LocalDate -> 문자열날짜
  @Override
  public String print(LocalDate object, Locale locale) {
    return DateTimeFormatter.ofPattern("yyyy-MM-dd").format(object);
  }
}
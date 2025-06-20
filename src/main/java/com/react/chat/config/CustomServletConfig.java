package com.react.chat.config;

import com.react.chat.controller.formatter.LocalDateFormatter;
import com.react.chat.controller.formatter.LocalDateTimeFormatter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CustomServletConfig implements WebMvcConfigurer {

  @Override
  public void addFormatters(FormatterRegistry registry) {
    registry.addFormatter(new LocalDateFormatter());
    registry.addFormatter(new LocalDateTimeFormatter());
  }

}

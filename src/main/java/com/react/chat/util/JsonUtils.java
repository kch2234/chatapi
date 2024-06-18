package com.react.chat.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.react.chat.domain.enumFiles.UserLanguages;

import java.io.IOException;
import java.util.List;

public class JsonUtils {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  public static List<UserLanguages> jsonToUserLanguages(String json) throws IOException {
    return objectMapper.readValue(json, new TypeReference<>() {
    });
  }
}



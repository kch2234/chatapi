package com.react.chat.domain.enumFiles;

public enum UserLanguages {
//  DE, RU, EN, UK, ID, JA, ZH, TR, FR, KO;
  DE("DE"), RU("RU"), EN("EN"), UK("UK"), ID("ID"), JA("JA"), ZH("ZH"), TR("TR"), FR("FR"), KO("KO");

  private final String value;

  UserLanguages(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
 }

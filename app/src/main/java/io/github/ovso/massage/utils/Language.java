package io.github.ovso.massage.utils;

public enum Language {
  KO("ko"), EN("en");

  private String value;

  Language(String $value) {
    this.value = $value;
  }

  public String get() {
    return value;
  }
}

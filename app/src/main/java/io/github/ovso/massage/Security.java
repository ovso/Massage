package io.github.ovso.massage;

import lombok.Getter;

public enum Security {
  AUTHORIZATION("KakaoAK 7296bb6b63d625d940275dbc7a78ae41"),

  API_BASE_URL(
      "https://dapi.kakao.com"),

  YOUTUBE_DEVELOPER_KEY(
      "AIzaSyBdY9vP4_vQs5YEGJ3Ghu6s5gGY8yFlo0s"),

  ADMOB_APP_ID(App.isDebug() ? "ca-app-pub-3940256099942544~3347511713"
      : "ca-app-pub-8679189423397017~4275586930"),

  ADMOB_UNIT_ID(App.isDebug() ? "ca-app-pub-3940256099942544/6300978111"
      : "ca-app-pub-8679189423397017/3924518551"),

  ADMOB_INTERSTITIAL_UNIT_ID(
      App.isDebug() ?
          "ca-app-pub-3940256099942544/1033173712" :
          "ca-app-pub-8679189423397017/5031831418");

  @Getter private String value;

  Security(String value) {
    this.value = value;
  }
}
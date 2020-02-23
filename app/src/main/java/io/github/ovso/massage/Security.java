package io.github.ovso.massage;

import lombok.Getter;

public enum Security {
    AUTHORIZATION("KakaoAK 7296bb6b63d625d940275dbc7a78ae41"),
    API_BASE_URL("https://dapi.kakao.com");

    @Getter
    private String value;

    Security(String value) {
        this.value = value;
    }
}
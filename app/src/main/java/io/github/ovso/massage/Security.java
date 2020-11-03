package io.github.ovso.massage;

public enum Security {
    AUTHORIZATION("KakaoAK 7296bb6b63d625d940275dbc7a78ae41"),
    API_BASE_URL("https://dapi.kakao.com");

    public String value;

    Security(String value) {
        this.value = value;
    }
}
package com.safety_signature.safety_signature_back.app.common.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * 소셜 로그인 타입 유형 코드
 * */
public enum SocialTypeCode {
    GOOGLE("GOOGLE"),
    KAKAO("KAKAO"),
    NAVER("NAVER");

    private String value;
    SocialTypeCode(String value) {this.value = value;}
    public String getValue() {return value;}
    @JsonCreator
    public static String from(String value) {
        for(SocialTypeCode type : SocialTypeCode.values()) {
            if(type.value.equals(value.toUpperCase())) {
                return type.getValue();
            }
        }
        return null;
    }
}

package com.safety_signature.safety_signature_back.app.common.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * 회원상태코드(회원계정원부의 회원계정별 상태를 관리)(계정관리)
 */
public enum UserStatusCode {
    PENDING("계정 신청중"),
    ACTIVE("계정 사용중"),
    DORMANT("계정 휴면중"),
    QUIT("계정 탈퇴중"),
    WITHDRAWAL("계정 탈퇴"),
    LOCKED("계정 잠김"),
    REJECT_BECOME("가입 거부"),
    RETRY_PENDING("계정 재신청중"),
    IMPOSSIBLE_BECOME("가입 불가");

    private final String value;

    UserStatusCode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @JsonCreator
    public static String from(String sub) {
        for (UserStatusCode code : UserStatusCode.values()) {
            if (code.getValue().equals(sub)) {
                return code.getValue();
            }
        }
        return null;
    }
}

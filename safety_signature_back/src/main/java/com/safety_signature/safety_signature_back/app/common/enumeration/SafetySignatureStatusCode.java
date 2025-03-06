package com.safety_signature.safety_signature_back.app.common.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * 공통 상태코드
 */
public enum SafetySignatureStatusCode {
    DRAFT("임시저장"),
    PUBLISHED("공개됨"),
    HIDDEN("숨김 처리됨"),
    DELETED("삭제 상태"),
    ARCHIVED("별도 보관 처리"),
    RESTRICTED("특정 기업에게만 공개됨"),
    PRIVATE("작성자 본인(또는 관리자)만 볼 수 있음"),
    EXPIRED("만료상태");

    private String value;

    SafetySignatureStatusCode(String value) {this.value = value;}

    public String getValue() { return value; }
    @JsonCreator
    public static String from(String value) {
        for (SafetySignatureStatusCode status : SafetySignatureStatusCode.values()) {
            if (status.getValue().equals(value)) {
                return status.getValue();
            }
        }
        return null;
    }
}

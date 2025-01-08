package com.safety_signature.safety_signature_back.app.common.enumeration;

/**
 * 승인구분코드(회원계정별 승인처리 정보를 관리)(계정관리)
 */
public enum ApproveTypeCode {
    APPROVE_BECOME("가입 승인"),
    LOCKED_BECOME("계정 잠김"),
    IMPOSSIBLE_BECOME("가입 불가"),
    REJECT_BECOME("가입 거부"),
    REJECT_QUIT("탈퇴 거부"),
    REQUEST_QUIT("탈퇴 요청");

    private final String value;

    ApproveTypeCode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
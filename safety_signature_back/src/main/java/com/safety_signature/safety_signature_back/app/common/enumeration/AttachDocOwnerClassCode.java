package com.safety_signature.safety_signature_back.app.common.enumeration;

/**
 * 첨부문서소유자유형코드(첨부문서를 소유한 자의 유형을 관리)(시스템관리)
 */
public enum AttachDocOwnerClassCode {
    USER_PROFILE("회원 프로필 이미지"),
    USER_SIGNATURE("회원 서명 이미지"),
    ;

    private final String value;

    AttachDocOwnerClassCode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

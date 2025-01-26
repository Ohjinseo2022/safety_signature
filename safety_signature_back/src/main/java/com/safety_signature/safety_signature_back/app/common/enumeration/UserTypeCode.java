package com.safety_signature.safety_signature_back.app.common.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * 회원 유형 코드 ( 마스터 관리자, 기업 관리자, 일반 회원 구분을 위한 코드
 */


public enum UserTypeCode {
    MASTER_ADMINISTRATOR("마스터 관리자 계정"),
    CORPORATE_MANAGER("기업관리자"),
    GENERAL_MEMBER("일반 회원");

    private final String value;

    UserTypeCode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @JsonCreator
    public static String from(String sub) {
        for (UserTypeCode code : UserTypeCode.values()) {
            if (code.getValue().equals(sub)) {
                return code.getValue();
            }
        }
        return null;
    }
}


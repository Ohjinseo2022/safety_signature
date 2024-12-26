package com.safety_signature.safety_signature_back.app.common.enumeration;

/**
 * 작업구분코드(첨부문서 업로드와 다운로드 정보를 관리)(시스템관리)
 */
public enum OperationClassCode {
    INSERT("등록"),
    UPDATE("변경"),
    DELETE("삭제");

    private final String value;

    OperationClassCode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

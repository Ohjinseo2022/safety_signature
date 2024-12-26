package com.safety_signature.safety_signature_back.app.common.enumeration;

/**
 * 작업구분코드(첨부문서 업로드와 다운로드 정보를 관리)(시스템관리)
 */
public enum OperationTypeCode {
    UP_LOADING("업로드"),
    DOWN_LOADING("다운로드"),
    DELETE_MINIO("MinIO 삭제");

    private final String value;

    OperationTypeCode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

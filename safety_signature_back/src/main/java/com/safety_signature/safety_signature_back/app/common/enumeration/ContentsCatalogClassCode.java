package com.safety_signature.safety_signature_back.app.common.enumeration;

/**
 * 콘텐츠카탈로그유형코드(콘텐츠 상품의 카탈로그유형 정보를 관리)(전시관리)
 */
public enum ContentsCatalogClassCode {
    CATEGORY_TYPE("카테고리 구분"),
    BUSINESS_NEEDS("사업 필수 요구"),
    PRICE("가격"),
    AVAILABILITY("가용성"),
    DATA_TYPE("데이터 타입"),
    PROVIDERS("공급사"),
    FILE_TYPE("파일유형"),
    DATA_AREA("데이터영역"),
    ;

    private final String value;

    ContentsCatalogClassCode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

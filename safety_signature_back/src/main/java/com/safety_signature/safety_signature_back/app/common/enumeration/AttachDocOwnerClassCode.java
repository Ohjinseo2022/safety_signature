package com.safety_signature.safety_signature_back.app.common.enumeration;

/**
 * 첨부문서소유자유형코드(첨부문서를 소유한 자의 유형을 관리)(시스템관리)
 */
public enum AttachDocOwnerClassCode {
    AFFILIATE_INTRODUCE("그룹 계열사 소개"),
    STUDY_GUIDE("학습 가이드"),
    CONTENTS_IMAGE("콘텐츠 이미지"),
    CONTENTS_DOCUMENT("콘텐츠 문서"),
    //CONTENTS_SAMPLE_DATA("콘텐츠 샘플 데이터");
    NORMAL_QNA("Q&A문의원부"),
    CONTENTS_INQUIRY("콘텐츠문의원부"),
    CUSTOM_CONTENTS_INQUIRY("맞춤형콘텐츠문의원부"),
    NOTICE_INFO("공지사항정보"),
    CONTENTS_DB("콘텐츠Db"),
    NEW_INFO("플랫폼소식정보"),
    BANNER_MANAGE("배너관리"),
    TREND_MONITOR("트렌드모니터"),
    TREND_PDF_INFO("트랜드모니터 PDF"),
    TREND_IMAGE_INFO("트랜드모니터 이미지"),
    POPUP_SCREEN("팝업(메인화면)"),
    NORMAL_FAQ("FAQ원부"),
    CONTENTS_SUBSCRIBE_DOC("콘텐츠구독문서"),
    LAST_ESTIMATE_DOC("최종견적서문서"),
    LAST_ESTIMATE_PROVIDERS_CLAUSE("최종견적서제공사약관"),
    TREND_CLIPPING("트렌드클리핑"),
    TREND_PDF_CLIPPING("트랜드클리핑 PDF"),
    TREND_IMAGE_CLIPPING("트랜드클리핑 이미지"),
    TREND_NEWSLETTER("트렌드뉴스레터"),
    TREND_PDF_NEWSLETTER("트랜드뉴스레터 PDF"),
    TREND_IMAGE_NEWSLETTER("트랜드뉴스레터 이미지"),
    NPD_CONCEPT_PRODUCT_IMAGE("NPD 신제품컨셉관리 이미지"),
    ;

    private final String value;

    AttachDocOwnerClassCode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

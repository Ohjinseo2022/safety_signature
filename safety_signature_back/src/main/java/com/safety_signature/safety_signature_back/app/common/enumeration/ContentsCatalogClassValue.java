package com.safety_signature.safety_signature_back.app.common.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * 콘텐츠카탈로그유형값(콘텐츠 상품의 카탈로그유형별 상세코드 정보를 관리)(전시관리)
 */
public enum ContentsCatalogClassValue {
    DISTRIBUTION_POS("유통/POS"),
    E_COMMERCE("이커머스"),
    PRICE_MARKET("가격/시세"),
    COMMERCIAL("상권"),
    FLOATING_POPULATION("유동인구"),
    SOCIAL("소셜"),
    ENVIRONMENT("환경"),
    PANEL_RESEARCH("패널조사"),
    SOURCE_DATA("원천데이터"),
    PROCESSING_DATA("가공데이터"),
    DEMAND_FORECAST("수요예측(시계열"),
    ANALYSIS_COMMERCIAL("상권분석(위치"),
    MARKET_INTELLIGENT("마켓인텔리전스"),
    ANALYSIS_PRICE("가격분석"),
    ANALYSIS_SALE("판매분석"),
    MANAGE_SUPPLY_CHAIN("공급망관리"),
    TREND_STATISTIC("트렌드/통계"),
    ETC("기타"),
    FREE_CHARGE("무료"),
    PAY_CHARGE("유료"),
    CONSULT_PRICE("가격협의"),
    SUBSCRIBE("구독"),
    NORMAL_DATA("정형 데이터 상품"),
    CUSTOM_MADE("주문/제작 상품"),
    DATA_SET("데이터셋"),
    REPORT("리포트"),
    COMPLEX("복합"),
    CUSTOM_DATA_SET("커스텀 데이터셋"),
    CUSTOM_REPORT("커스텀 리포트"),
    CUSTOM_COMPLEX("커스텀 복합"),
    MARKET_LINK("마켓링크"),
    BUILT_ON("빌트온"),
    TRIDGE("트릿지"),
    LOPLAT("로플랫"),
    BIG_VALUE("빅벨류"),
    VIVE("바이브"),
    DATA_MARKETING("데이터마케팅코리아"),
    WEATHER_I("웨더아이"),
    M_CORPROATION("엠코퍼레이션"),
    MACROMIL_EMBRAIN("마크로밀엠브레인"),
    KANTAR("KANTAR"),
    SHINHAN_CARD("신한카드"),
    CSV("CSV"),
    XML("XML"),
    JSON("JSON"),
    PDF("PDF"),
    XLSX("XLSX"),
    PPT("PPT"),
    OPENING_UP_DATA("개방데이터"),
    PUBLIC_DATA("공공데이터"),
    ;

    private final String value;

    ContentsCatalogClassValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @JsonCreator
    public static String from(String sub) {
        for (ContentsCatalogClassValue contentsCatalogClassValue : ContentsCatalogClassValue.values()) {
            if (contentsCatalogClassValue.getValue().equals(sub)) {
                return contentsCatalogClassValue.getValue();
            }
        }
        return null;
    }

}

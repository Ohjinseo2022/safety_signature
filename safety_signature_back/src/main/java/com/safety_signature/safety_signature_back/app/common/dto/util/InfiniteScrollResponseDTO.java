package com.safety_signature.safety_signature_back.app.common.dto.util;


import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;


@Getter
public class InfiniteScrollResponseDTO<T> {
    private final List<T> data; // 데이터 목록
    private final Meta meta; // 메타 정보

    @Getter
    @NoArgsConstructor
    public static class Meta {
        private int count; // 현재 가져온 데이터 개수
        private boolean hasMore; // 다음 데이터 존재 여부
        private String nextCursor; // 다음 데이터 요청을 위한 커서 값

        public Meta(int count, boolean hasMore, String nextCursor) {
            this.count = count;
            this.hasMore = hasMore;
            this.nextCursor = nextCursor;
        }
    }
    public InfiniteScrollResponseDTO(List<T> data, int count, boolean hasMore, String nextCursor) {
        this.data = data;
        this.meta = new Meta(count, hasMore, nextCursor);
    }

    /**
     * Page 객체를 받아 InfiniteScrollResponse로 변환
     */
    public static <T> InfiniteScrollResponseDTO<T> from(Page<T> page, String nextCursor,boolean hasMore) {
        return new InfiniteScrollResponseDTO<>(
                page.getContent(),
                page.getNumberOfElements(),
                hasMore,
                nextCursor
        );
    }

}

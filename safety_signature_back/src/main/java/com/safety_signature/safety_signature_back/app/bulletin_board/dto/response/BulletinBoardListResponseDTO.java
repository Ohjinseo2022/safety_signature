package com.safety_signature.safety_signature_back.app.bulletin_board.dto.response;

import com.fasterxml.jackson.annotation.JsonView;
import com.safety_signature.safety_signature_back.app.bulletin_board.dto.BulletinBoardMasterDTO;
import com.safety_signature.safety_signature_back.app.common.dto.View;
import com.safety_signature.safety_signature_back.config.Partial;
import com.safety_signature.safety_signature_back.utils.DateUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.antlr.v4.runtime.CodePointBuffer;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BulletinBoardListResponseDTO extends BulletinBoardResponseBaseDTO{

    private List<BulletinBoardMasterCustomDTO> data;

    private long totalCount; // 필요 없을지도 몰라용

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BulletinBoardMasterCustomDTO extends BulletinBoardMasterDTO{

        @Schema(description = "최소 생성 일시 문자열 포멧")
        private String createdDateFormat;
        private long signatureCount;

        public static BulletinBoardMasterCustomDTO from(BulletinBoardMasterDTO dto) {
            // 가장 편한 방법이지만 가독성이 떨어질 수 있다는 단점이있음!
            BulletinBoardMasterCustomDTO customDTO = new BulletinBoardMasterCustomDTO();
            BeanUtils.copyProperties(dto, customDTO);
            // 추가 필드 값 설정
            customDTO.setCreatedDateFormat(DateUtil.instantToStringDate(dto.getCreatedDate(), "yyyy-MM-dd HH:mm"));
            customDTO.setSignatureCount(Optional.ofNullable(customDTO.getSignatureCount()).orElse(0L));

            return customDTO;
        }
    }


}



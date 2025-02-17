package com.safety_signature.safety_signature_back.app.bulletin_board.dto.custom;

import com.safety_signature.safety_signature_back.app.bulletin_board.dto.BulletinBoardMasterDTO;
import com.safety_signature.safety_signature_back.app.common.dto.AttachDocMasterDTO;
import com.safety_signature.safety_signature_back.utils.DateUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BulletinBoardMasterCustomDTO extends BulletinBoardMasterDTO {

    @Schema(description = "최소 생성 일시 문자열 포멧")
    private String createdDateFormat;
    @Schema(description = "전자결제 완료 인원수")
    private long signatureCount;
    @Schema(description = "전자결제 문서 첨부 파일")
    private List<AttachDocMasterDTO> attachDocList;

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

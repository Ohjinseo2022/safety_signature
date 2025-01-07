package com.safety_signature.safety_signature_back.app.bulletin_board.dto;

import com.safety_signature.safety_signature_back.app.common.dto.AbstractAuditingDTO;
import com.safety_signature.safety_signature_back.app.user.dto.UserMasterDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@Schema(description = "게시판 첨부 문서 정보 테이블")
@Data
@EqualsAndHashCode(callSuper = true,of="id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BulletinBoardAttachInfoDTO extends AbstractAuditingDTO<String> implements Serializable {
    @Size(max = 36)
    @Schema(description = "게시판 첨부문서 고유 ID" ,requiredMode= Schema.RequiredMode.REQUIRED)
    private String id;

    @Size(max = 36)
    @Schema(description = "첨부 문서 ID" ,requiredMode= Schema.RequiredMode.REQUIRED)
    private String attach_doc_id;

    @Size(max = 36)
    @Schema(description = "게시글 오너 ID" ,requiredMode= Schema.RequiredMode.REQUIRED)
    private String userMasterId;

    @Schema(description = "게시글 오너 ID")
    private UserMasterDTO userMasterDTO;
}

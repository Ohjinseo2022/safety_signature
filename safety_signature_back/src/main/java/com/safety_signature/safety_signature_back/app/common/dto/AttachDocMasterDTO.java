package com.safety_signature.safety_signature_back.app.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Setter
@Getter
@Schema(description = "첨부 문서 관리 테이블")
@Data
@EqualsAndHashCode(callSuper = true,of="id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttachDocMasterDTO  extends AbstractAuditingDTO<String> implements Serializable {

    @Size(max=36)
    @Schema(description = "첨부 문서 고유 ID",requiredMode= Schema.RequiredMode.REQUIRED)
    private String id;

    @Size(max=36)
    @Schema(description = "첨부 문서 소유 ID",requiredMode= Schema.RequiredMode.REQUIRED)
    private String ownerId;

    @Size(max=1000)
    @Schema(description = "첨부문서 이름",requiredMode= Schema.RequiredMode.REQUIRED)
    private String attachName;

    @Size(max=20)
    @Schema(description = "첨부문서 확장자",requiredMode= Schema.RequiredMode.REQUIRED)
    private String attachFileType;

    @Size(max=5000)
    @Schema(description = "첨부문서 확장자",requiredMode= Schema.RequiredMode.REQUIRED)
    private String attachMinioPath;

    @Schema(description = "다운로드 가능 여부")
    private Boolean attachDownloadYn;

    @Schema(description = "첨부 문서 용량 KB 단위",requiredMode= Schema.RequiredMode.REQUIRED)
    private BigDecimal attachSize;
}

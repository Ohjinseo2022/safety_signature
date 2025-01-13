package com.safety_signature.safety_signature_back.app.common.dto;

import com.safety_signature.safety_signature_back.app.common.enumeration.AttachDocOwnerClassCode;
import com.safety_signature.safety_signature_back.app.common.enumeration.ContentsCatalogClassValue;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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

    @Size(max=100)
    @Schema(description = "첨부 문서 소유 ID",requiredMode= Schema.RequiredMode.REQUIRED)
    private String attachDocOwnerId;

    @Size(max=1000)
    @Schema(description = "첨부문서 이름",requiredMode= Schema.RequiredMode.REQUIRED)
    private String attachDocName;

    @Size(max=20)
    @Schema(description = "첨부문서 확장자",requiredMode= Schema.RequiredMode.REQUIRED)
    private String attachFileType;

    @Size(max = 4000)
    @Schema(description = "첨부문서설명")
    private String attachDocExplain;

    @Size(max = 100)
    @Schema(description = "첨부문서ID")
    private String attachDocId;

    @Schema(description = "첨부문서위치")
    private String attachDocPosition;

    @Schema(description = "첨부문서소유자유형코드",requiredMode= Schema.RequiredMode.REQUIRED)
    private AttachDocOwnerClassCode attachDocOwnerClassCode;


    private ContentsCatalogClassValue classValue;

    private String attachDocReader;

    private List<List<String>> attachDocReaderXlsx;

    @Schema(description = "첨부문서위치_파일명")
    private String attachDocPositionFilename;

    @Schema(description = "첨부문서크기")
    private BigDecimal attachDocSize;

    /**
     * 첨부문서분할내역 LIST
     */

    @Schema(description = "첨부문서분할내역 LIST")
    private List<AttachDocPartitionDTO> attachDocPartitionDTO;

    /**
     * 첨부문서다운로드제한일시
     */
    @Schema(description = "첨부문서다운로드제한일시")
    private LocalDateTime attachDownloadLimitDtime;

    /**
     * 첨부문서보관제한일시
     */
    @Schema(description = "첨부문서보관제한일시")
    private LocalDateTime attachStorageLimitDtime;

    /**
     * MinIO삭제여부
     */
    @Schema(description = "MinIO삭제여부")
    private Boolean minioDeleteYn = false;
}

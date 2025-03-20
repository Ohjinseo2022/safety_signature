package com.safety_signature.safety_signature_back.app.approve_master.dto;

import com.safety_signature.safety_signature_back.app.common.dto.AbstractAuditingDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;

@Setter
@Getter
@Schema(description = "전자결제 명단 테이블")
@Data
@EqualsAndHashCode(callSuper = true,of="id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApproveMasterDTO extends AbstractAuditingDTO<String> implements Serializable {
    @Size(max = 36)
    @Schema(description = "전자결제 명단 고유 ID" ,requiredMode= Schema.RequiredMode.REQUIRED)
    private String id;

    @Size(max = 36)
    @Schema(description = "게시글 고유 ID" ,requiredMode= Schema.RequiredMode.REQUIRED)
    private String bulletinBoardId;

    @Size(max = 36)
    @Schema(description = "회원 고유 ID" ,requiredMode= Schema.RequiredMode.REQUIRED)
    private String userMasterId;

    @Size(max =100)
    @Schema(description = "회원 이름" ,requiredMode= Schema.RequiredMode.REQUIRED)
    private String userName;

    @Size(max =36)
    @Schema(description = "첨부문서 ID" ,requiredMode= Schema.RequiredMode.REQUIRED)
    private String attachDocId;

    @Size(max =100)
    @Schema(description = "결제 상태 코드" )
    private String approveStatus;


    @Size(max = 1000)
    @Schema(description = "업체명" )
    private String companyName;

    @Size(max = 1000)
    @Schema(description = "공사 종목명" )
    private String constructionBusiness;

    @Schema(description = "최소 생성 일시")
    private Instant createdDate;


    @Schema(description = "최소 생성자")
    private String createdBy;

    @Schema(description = "마지막 변경자")
    private String lastModifiedBy;

    @Schema(description = "마지막 변경 일시")
    private Instant lastModifiedDate;
}

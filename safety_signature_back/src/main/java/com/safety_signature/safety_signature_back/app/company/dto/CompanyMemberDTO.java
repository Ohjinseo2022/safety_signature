package com.safety_signature.safety_signature_back.app.company.dto;

import com.safety_signature.safety_signature_back.app.common.dto.AbstractAuditingDTO;
import com.safety_signature.safety_signature_back.app.user.dto.UserMasterDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
@Setter
@Getter
@Schema(description = "회사 구성원 정보 테이블")
@Data
@EqualsAndHashCode(callSuper = true,of="id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyMemberDTO extends AbstractAuditingDTO<String> implements Serializable {
    @Size(max = 36)
    @Schema(description = "회사 구성원 고유 ID" ,requiredMode= Schema.RequiredMode.REQUIRED)
    private String id;

    @Size(max = 100)
    @Schema(description = "활성 상태" )
    private String jobPosition;

    @Size(max = 100)
    @Schema(description = "활성 상태" )
    private String statusCode;

    @Size(max = 36)
    @Schema(description = "회원 관리 고유 ID" ,requiredMode= Schema.RequiredMode.REQUIRED)
    private String userMasterId;

    @Size(max = 36)
    @Schema(description = "회원 고유 ID",requiredMode= Schema.RequiredMode.REQUIRED)
    private UserMasterDTO userMasterDTO;

    @Size(max = 36)
    @Schema(description = "회사 정보 고유 ID" ,requiredMode= Schema.RequiredMode.REQUIRED)
    private String companyMasterId;

    @Size(max = 36)
    @Schema(description = "회사 정보 고유 ID",requiredMode= Schema.RequiredMode.REQUIRED)
    private CompanyMasterDTO companyMasterDTO;
}

package com.safety_signature.safety_signature_back.app.company.dto;

import com.safety_signature.safety_signature_back.app.common.dto.AbstractAuditingDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@Schema(description = "회사정보 테이블")
@Data
@EqualsAndHashCode(callSuper = true,of="id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyMasterDTO  extends AbstractAuditingDTO<String> implements Serializable {
    @Size(max = 36)
    @Schema(description = "회사고유 ID" ,requiredMode= Schema.RequiredMode.REQUIRED)
    private String id;

    @Size(max = 1000)
    @Schema(description = "회사명" ,requiredMode= Schema.RequiredMode.REQUIRED)
    private String companyName;

    @Size(max = 1000)
    @Schema(description = "사업자 번호" )
    private String businessNumber;

    @Size(max = 100)
    @Schema(description = "사업자 대표" )
    private String businessRepresentative;

    @Size(max = 100)
    @Schema(description = "담당자 이름" )
    private String agentName;

    @Size(max = 1000)
    @Schema(description = "담당자 연락처" )
    private String agentPhoneNumber;

    @Size(max = 100)
    @Schema(description = "회사상태코드" )
    private String companyStatusCode;

    @Size(max = 100)
    @Schema(description = "회사유형" )
    private String companyType;

}

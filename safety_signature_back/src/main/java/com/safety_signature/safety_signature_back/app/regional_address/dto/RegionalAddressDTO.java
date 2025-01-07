package com.safety_signature.safety_signature_back.app.regional_address.dto;

import com.safety_signature.safety_signature_back.app.common.dto.AbstractAuditingDTO;

import com.safety_signature.safety_signature_back.app.company.dto.CompanyMasterDTO;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Size;
import lombok.*;



import java.io.Serializable;

@Setter
@Getter
@Schema(description = "근무지역정보 테이블")
@Data
@EqualsAndHashCode(callSuper = true,of="id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegionalAddressDTO extends AbstractAuditingDTO<String> implements Serializable {
    @Size(max = 36)
    @Schema(description = "근무지역정보 고유 ID" ,requiredMode= Schema.RequiredMode.REQUIRED)
    private String id;

    @Size(max =100)
    @Schema(description = "우편 번호")
    private String zoneCode;

    @Size(max =5000)
    @Schema(description = "도로명 주소")
    private String roadAddress;

    @Size(max =5000)
    @Schema(description = "지번 주소")
    private String jibunAddress;

    @Size(max =5000)
    @Schema(description = "상세 주소")
    private String detailAddress;

    @Size(max =1000)
    @Schema(description = "위도")
    private String latitude;
    @Size(max =1000)
    @Schema(description = "경도")
    private String longitude;
    @Size(max = 36)
    @Schema(description = "회사 정보 고유 ID")
    private String companyMasterId;

    @Schema(description = "회사 정보 고유 ID")
    private CompanyMasterDTO companyMasterDTO;
}

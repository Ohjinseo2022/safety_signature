package com.safety_signature.safety_signature_back.app.token.dto;

import com.safety_signature.safety_signature_back.app.common.dto.AbstractAuditingDTO;
import com.safety_signature.safety_signature_back.app.user.dto.UserMasterDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
@Setter
@Getter
@Schema(description = "토큰 관리 테이블")
@Data
@EqualsAndHashCode(callSuper = true,of="id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenManagementMaterDTO extends AbstractAuditingDTO<String> implements Serializable {

    @Size(max = 36)
    @Schema(description = "토큰 관리 고유 ID" ,requiredMode= Schema.RequiredMode.REQUIRED)
    private String id;

    @Size(max = 36)
    @Schema(description = "회원 관리 고유 ID")
    private String userMasterId;

    @Size(max = 36)
    @Schema(description = "회원 고유 ID",requiredMode= Schema.RequiredMode.REQUIRED)
    private UserMasterDTO userMasterDTO;

    @Size(max=5000)
    @Schema(description = "엑세스 토큰")
    private String accessToken;

    @Size(max=5000)
    @Schema(description = "리프레시 토큰")
    private String refreshToken;
}

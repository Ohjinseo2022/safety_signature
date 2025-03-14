package com.safety_signature.safety_signature_back.app.common.dto;

import com.safety_signature.safety_signature_back.app.common.enumeration.UserTypeCode;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@Schema(description = "메뉴 관리 테이블")
@Data
@EqualsAndHashCode(callSuper = true,of="id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuManagementDTO  extends AbstractAuditingDTO<String> implements Serializable {

    @Size(max=36)
    @Schema(description = "메뉴 관리 테이블 고유 ID",requiredMode= Schema.RequiredMode.REQUIRED)
    private String id;

    @Size(max=1000)
    @Schema(description = "메뉴 이름")
    private String menuName;

    @Size(max=5000)
    @Schema(description = "메뉴 경로")
    private String menuPath;

    @Size(max=36)
    @Schema(description = "상위메뉴 ID")
    private String parentId;

    @Size(max=1000)
    @Schema(description = "메뉴 접근권한")
    private UserTypeCode menuAuthCode;

    @Schema(description = "메뉴 사용유무")
    private boolean isMenuActive;
}

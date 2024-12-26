package com.safety_signature.safety_signature_back.app.common.dto;

import com.safety_signature.safety_signature_back.app.common.enumeration.OperationTypeCode;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.safety_signature.safety_signature_back.app.common.domain.AttachDocMaster} entity.
 */
@Schema(description = "첨부문서이력")
@SuppressWarnings("common-java:DuplicatedBlocks")
@Data @EqualsAndHashCode(callSuper = false, of="id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttachDocHistDTO extends AbstractAuditingDTO<String> implements Serializable {

    @Size(max = 36)
    private String id;

    /**
     * 작업구분코드
     */
    @NotNull
    @Schema(description = "작업구분코드", required = true)
    private OperationTypeCode operationTypeCode;


    /**
     * 작업목적설명
     */
    @Size(max = 4000)
    @Schema(description = "작업목적설명")
    private String operationGoalExplain;




    /**
     * 작업자IP주소
     */
    @Schema(description = "작업자IP주소")
    private String operatorIpAddress;


    private AttachDocMasterDTO attachDocMaster;
}

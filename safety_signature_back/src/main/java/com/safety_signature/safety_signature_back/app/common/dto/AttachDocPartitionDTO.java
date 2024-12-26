package com.safety_signature.safety_signature_back.app.common.dto;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonView;
import com.safety_signature.safety_signature_back.config.FieldSelector;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 */
@Schema(description = "첨부문서분할내역")
@SuppressWarnings("common-java:DuplicatedBlocks")
@Data
@EqualsAndHashCode(callSuper = false, of="id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonFilter(FieldSelector.FILTER_NAME)
public class AttachDocPartitionDTO extends AbstractAuditingDTO<String> implements Serializable {

    @JsonView(View.Summary.class)
    @Size(max = 36)
    private String id;

    /**
     * 페이지순번
     */
    @JsonView(View.Summary.class)
    @Schema(description = "페이지순번")
    private Long pageTurn;

    /**
     * 첨부문서ID
     */
    @JsonView(View.Summary.class)
    @Size(max = 100)
    @Schema(description = "첨부문서ID")
    private String attachDocId;

    /**
     * 첨부문서위치
     */
    @JsonView(View.Summary.class)
    @Size(max = 1000)
    @Schema(description = "첨부문서위치")
    private String attachDocPosition;

    /**
     * 첨부문서크기
     */
    @JsonView(View.Summary.class)
    @Schema(description = "첨부문서크기")
    private BigDecimal attachDocSize;

    private AttachDocMasterDTO attachDocMasterDTO;
}

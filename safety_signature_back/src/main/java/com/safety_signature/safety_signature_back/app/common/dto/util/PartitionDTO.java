package com.safety_signature.safety_signature_back.app.common.dto.util;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Schema(description = "imgPartitionDTO")
@SuppressWarnings("common-java:DuplicatedBlocks")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartitionDTO {

    private Long pageTurn;

    private String attachDocPosition;

    private BigDecimal attachDocSize;

}

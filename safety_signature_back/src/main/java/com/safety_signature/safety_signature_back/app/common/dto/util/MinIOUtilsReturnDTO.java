package com.safety_signature.safety_signature_back.app.common.dto.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MinIOUtilsReturnDTO {
    String attachDocPosition;
    String attachDocName;
    BigDecimal attachDocSize;

}

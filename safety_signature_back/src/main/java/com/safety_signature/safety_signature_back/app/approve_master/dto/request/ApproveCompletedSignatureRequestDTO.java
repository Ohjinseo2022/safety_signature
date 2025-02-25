package com.safety_signature.safety_signature_back.app.approve_master.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ApproveCompletedSignatureRequestDTO {
    @NotNull
    String bulletinBoardId;
}

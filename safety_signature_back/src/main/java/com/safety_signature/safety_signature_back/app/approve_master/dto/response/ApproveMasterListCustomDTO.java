package com.safety_signature.safety_signature_back.app.approve_master.dto.response;

import com.safety_signature.safety_signature_back.app.common.dto.ResponseDTO;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@Builder
@EqualsAndHashCode(callSuper=false)
public class ApproveMasterListCustomDTO extends ResponseDTO {
    private List<List<ApproveMasterCustomDTO>> evenData;
    private List<List<ApproveMasterCustomDTO>> oddData;
}

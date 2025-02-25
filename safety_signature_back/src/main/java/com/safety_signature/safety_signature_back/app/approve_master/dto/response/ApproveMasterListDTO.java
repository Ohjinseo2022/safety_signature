package com.safety_signature.safety_signature_back.app.approve_master.dto.response;

import com.safety_signature.safety_signature_back.app.approve_master.dto.ApproveMasterDTO;
import com.safety_signature.safety_signature_back.app.common.dto.ResponseDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ApproveMasterListDTO extends ResponseDTO {
    private List<ApproveMasterCustomDTO> data;
}

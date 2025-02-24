package com.safety_signature.safety_signature_back.app.approve_master.dto.response;

import com.safety_signature.safety_signature_back.app.common.dto.ResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApproveMasterMessageDTO extends ResponseDTO {
    HttpStatus httpStatus;
    String message;
}

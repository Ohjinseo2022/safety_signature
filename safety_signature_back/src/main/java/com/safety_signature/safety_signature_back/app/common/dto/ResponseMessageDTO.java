package com.safety_signature.safety_signature_back.app.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseMessageDTO extends ResponseDTO {
    HttpStatus httpStatus;
    String message;
}

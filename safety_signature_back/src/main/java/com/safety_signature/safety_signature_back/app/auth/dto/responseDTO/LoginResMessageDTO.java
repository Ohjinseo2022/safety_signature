package com.safety_signature.safety_signature_back.app.auth.dto.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResMessageDTO extends LoginResBaseDTO {
    private String message;
    private HttpStatus httpStatus;
}

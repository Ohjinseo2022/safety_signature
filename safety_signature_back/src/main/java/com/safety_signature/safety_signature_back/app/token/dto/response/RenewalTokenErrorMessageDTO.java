package com.safety_signature.safety_signature_back.app.token.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RenewalTokenErrorMessageDTO extends ResponseTokenManagementBaseDTO{
    private String massage;
    private HttpStatus errorCode;
}

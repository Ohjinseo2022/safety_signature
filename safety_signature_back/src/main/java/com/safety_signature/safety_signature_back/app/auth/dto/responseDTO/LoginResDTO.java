package com.safety_signature.safety_signature_back.app.auth.dto.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResDTO extends LoginResBaseDTO{
    String accessToken;
    String refreshToken;
}

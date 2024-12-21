package com.safety_signature.safety_signature_back.app.token.dto.response;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RenewalTokenSuccessDTO extends ResponseTokenManagementBaseDTO{
    private String accessToken;
    private String refreshToken;
}

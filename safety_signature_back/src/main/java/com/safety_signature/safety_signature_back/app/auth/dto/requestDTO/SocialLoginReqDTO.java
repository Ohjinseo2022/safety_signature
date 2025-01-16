package com.safety_signature.safety_signature_back.app.auth.dto.requestDTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@JsonFilter(FieldSelector.FILTER_NAME)
public class SocialLoginReqDTO {
    @NotNull
    String socialType;
    String name;
    String mobile;
    String email;
    String password;
}

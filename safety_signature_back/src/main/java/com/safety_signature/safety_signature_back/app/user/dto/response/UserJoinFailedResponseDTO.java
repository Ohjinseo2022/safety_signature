package com.safety_signature.safety_signature_back.app.user.dto.response;

import com.safety_signature.safety_signature_back.app.common.enumeration.UserStatusCode;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserJoinFailedResponseDTO {
    final UserStatusCode userStatusCode;
    final String message;
}

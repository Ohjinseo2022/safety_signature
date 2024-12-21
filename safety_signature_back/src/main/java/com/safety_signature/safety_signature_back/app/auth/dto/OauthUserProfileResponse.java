package com.safety_signature.safety_signature_back.app.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OauthUserProfileResponse(
        String name,
        String email,
        @JsonProperty("picture") String profileImageUri
) {
}

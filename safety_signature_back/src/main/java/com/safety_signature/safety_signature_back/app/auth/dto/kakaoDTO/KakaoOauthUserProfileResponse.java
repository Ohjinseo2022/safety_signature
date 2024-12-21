package com.safety_signature.safety_signature_back.app.auth.dto.kakaoDTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public record KakaoOauthUserProfileResponse(
        String name,
        String email,
        @JsonProperty("kakao_account.email") String profileImageUri
) {
}

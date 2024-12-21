package com.safety_signature.safety_signature_back.app.auth.oauth2;

import com.safety_signature.safety_signature_back.app.auth.dto.OauthUserProfileResponse;
import com.safety_signature.safety_signature_back.app.auth.dto.kakaoDTO.KakaoOauthDTO;

public interface OAuthWebClient {
    OauthUserProfileResponse requestGoogleOauthUserProfile(String accessToken);

    KakaoOauthDTO requestKakaoOauthUserProfile(String accessToken);
}

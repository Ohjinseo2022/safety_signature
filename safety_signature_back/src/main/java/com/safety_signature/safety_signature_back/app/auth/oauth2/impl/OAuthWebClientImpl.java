package com.safety_signature.safety_signature_back.app.auth.oauth2.impl;

import com.safety_signature.safety_signature_back.app.auth.dto.OauthUserProfileResponse;
import com.safety_signature.safety_signature_back.app.auth.dto.kakaoDTO.KakaoOauthDTO;
import com.safety_signature.safety_signature_back.app.auth.exception.OauthServerException;
import com.safety_signature.safety_signature_back.app.auth.oauth2.OAuthWebClient;
import com.safety_signature.safety_signature_back.utils.RegexUtils;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class OAuthWebClientImpl implements OAuthWebClient {
    private static final String GOOGLE_USER_PROFILE_URI = "https://www.googleapis.com/oauth2/v3/userinfo";
    private static final String KAKAO_USER_PROFILE_URI = "https://kapi.kakao.com/v2/user/me";
    @Override
    public OauthUserProfileResponse requestGoogleOauthUserProfile(String accessToken) {
        return WebClient.create()
                .post()
                .uri(GOOGLE_USER_PROFILE_URI)
                .headers(headers -> headers.setBearerAuth(accessToken))
                .retrieve()
                .onStatus(HttpStatusCode::isError, response -> response.bodyToMono(String.class)
                        .map(OauthServerException::new))
                .bodyToMono(OauthUserProfileResponse.class)
                .block();
    }

    @Override
    public KakaoOauthDTO requestKakaoOauthUserProfile(String accessToken) {
        return WebClient.create()
                .post()
                .uri(
                        KAKAO_USER_PROFILE_URI
//                        uriBuilder -> uriBuilder.path(KAKAO_USER_PROFILE_URI)
//                        .queryParam("property_keys","['kakao_account.profile','kakao_account.name','kakao_account.email','phone_number']").build()
                )
                .headers(headers->{
                    headers.setBearerAuth(accessToken);
                    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                })
                .retrieve()
                .onStatus(HttpStatusCode::isError, response -> response.bodyToMono(String.class)
                        .map(OauthServerException::new))
                .bodyToMono(KakaoOauthDTO.class)
                .map(this::convertPhoneNumber)
                .block();
    }
    // 전화번호 변환 로직
    private KakaoOauthDTO convertPhoneNumber(KakaoOauthDTO kakaoOauthDTO) {
        if (kakaoOauthDTO != null && kakaoOauthDTO.getKakao_account().getPhone_number() != null) {
            // phone_number 변환
            String convertedPhoneNumber = RegexUtils.convertPhoneNumber(kakaoOauthDTO.getKakao_account().getPhone_number());
            kakaoOauthDTO.getKakao_account().setPhone_number(convertedPhoneNumber);
        }
        return kakaoOauthDTO;
    }
}

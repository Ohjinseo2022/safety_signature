package com.safety_signature.safety_signature_back.app.auth.dto.kakaoDTO;


import lombok.*;

@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KakaoOauthDTO {
    Long id;
    String connected_at;
    Properties properties;
    KakaoAccount kakao_account;
}


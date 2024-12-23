package com.safety_signature.safety_signature_back.app.auth.dto.kakaoDTO;

import lombok.*;

@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KakaoAccount{
    /**
     * 동의 항목이 추가된다면 해당 DTO에 추가해줘야 정상적으로 받아올수있다!
     */
    boolean profile_nickname_needs_agreement;
    boolean profile_image_needs_agreement;
    KakaoAccountProfile profile;
    boolean has_email;
    boolean email_needs_agreement;
    boolean is_email_valid;
    boolean is_email_verified;
    String email;
    String phone_number;
    String name;
}

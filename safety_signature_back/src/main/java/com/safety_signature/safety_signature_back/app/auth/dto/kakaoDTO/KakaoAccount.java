package com.safety_signature.safety_signature_back.app.auth.dto.kakaoDTO;

import lombok.*;

@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KakaoAccount{
    boolean profile_nickname_needs_agreement;
    boolean profile_image_needs_agreement;
    KakaoAccountProfile profile;
    boolean has_email;
    boolean email_needs_agreement;
    boolean is_email_valid;
    boolean is_email_verified;
    String email;
}

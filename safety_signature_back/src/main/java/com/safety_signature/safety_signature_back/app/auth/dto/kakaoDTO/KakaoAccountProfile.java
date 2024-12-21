package com.safety_signature.safety_signature_back.app.auth.dto.kakaoDTO;

import lombok.*;

@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KakaoAccountProfile{
    String nickname;
    String thumbnail_image_url;
    String profile_image_url;
    boolean is_default_image;
    boolean is_default_nickname;
}

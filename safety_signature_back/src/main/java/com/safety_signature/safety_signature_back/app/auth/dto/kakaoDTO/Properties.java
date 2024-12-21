package com.safety_signature.safety_signature_back.app.auth.dto.kakaoDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Properties{
    String nickname;
    //하단 방식을 사용하면 결과 정보를 내가 원하는 변수명으로 파싱할수있음!
    @JsonProperty("profile_image")
    String profile_image;
    String thumbnail_image;
}

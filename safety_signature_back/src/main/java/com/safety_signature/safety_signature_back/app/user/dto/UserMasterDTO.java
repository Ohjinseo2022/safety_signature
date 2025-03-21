package com.safety_signature.safety_signature_back.app.user.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.safety_signature.safety_signature_back.app.common.dto.AbstractAuditingDTO;
import com.safety_signature.safety_signature_back.app.common.dto.View;
import com.safety_signature.safety_signature_back.app.common.enumeration.UserStatusCode;
import com.safety_signature.safety_signature_back.app.common.enumeration.UserTypeCode;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.*;


import java.io.Serializable;
@Setter
@Getter
@Schema(description = "회원 정보 테이블")
@Data @EqualsAndHashCode(callSuper = true,of="id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserMasterDTO  extends AbstractAuditingDTO<String> implements Serializable {

    @JsonView(View.Min.class)
    @Size(max = 36)
    @Schema(description = "회원 고유 ID" ,requiredMode= Schema.RequiredMode.REQUIRED)
    private String id;

    @JsonView(View.Min.class)
    @Size(max = 36)
    @Schema(description = "회원 이름" )//,requiredMode= Schema.RequiredMode.REQUIRED 카카오 개발자 승인이후 수정예정임!
    private String userName;

    @JsonView(View.Min.class)
    @Size(max = 2000)
    @Schema(description = "핸드폰 번호" )
    private String mobile;


    @JsonView(View.Hidden.class)
    @Size(max = 5000)
    @Schema(description = "회원 비밀번호")
    private String userPassword;

    @JsonView(View.Min.class)
    @Size(max = 100)
    @Schema(description = "회원 이메일",requiredMode= Schema.RequiredMode.REQUIRED)
    private String email;

    @JsonView(View.Min.class)
    @Size(max = 5000)
    @Schema(description = "회원 프로필 이미지 URL")
    private String profileImageUri;

    /**
     * 로그인 플랫폼 종류
     */
    @JsonView(View.Detail.class)
    @Schema(description = "구글연동유무")
    private boolean googleSignIn=false;

    @JsonView(View.Detail.class)
    @Schema(description = "카카오연동유무")
    private boolean kakaoSignIn=false;

    @JsonView(View.Detail.class)
    @Schema(description = "네이버연동유무")
    private boolean naverSignIn=false;

    @JsonView(View.Min.class)
    @Schema(description="회원 서명 ID")
    private String signatureDocId;

    @JsonView(View.Min.class)
    @Schema(description="회원 타입 코드")
    private UserTypeCode userTypeCode;

    @JsonView(View.Min.class)
    @Schema(description="계정 상태 코드")
    private UserStatusCode userStatusCode;
}

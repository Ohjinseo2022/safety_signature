package com.safety_signature.safety_signature_back.app.bulletin_board.dto.request;

import com.safety_signature.safety_signature_back.app.common.enumeration.SafetySignatureStatusCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BulletinBoardRegistrationRequestDTO {

    @Schema(description = "전자결제 문서 고유 ID")
    private String bulletinBoardId;

    @NotNull
    @Schema(description = "전자문서 제목")
    private String boardTitle;

    @NotNull
    @Schema(description = "전자문서 설명 & 내용")
    private String boardContents;


    @Schema(description = "현장 주소 정보")
    private String siteAddress;

    @Schema(description = "현장명")
    private String siteName;

    @Schema(description = "전자결제 문서 상태 코드")
    private SafetySignatureStatusCode statusCode;
}

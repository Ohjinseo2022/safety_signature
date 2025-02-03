package com.safety_signature.safety_signature_back.app.bulletin_board.dto.request;

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
    @NotNull
    @Schema(description = "전자문서 제목")
    private String boardTitle;
    @NotNull
    @Schema(description = "전자문서 설명 & 내용")
    private String boardContents;

}

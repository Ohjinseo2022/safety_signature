package com.safety_signature.safety_signature_back.app.bulletin_board.dto;

import com.safety_signature.safety_signature_back.app.common.dto.AbstractAuditingDTO;
import com.safety_signature.safety_signature_back.app.user.domain.UserMaster;
import com.safety_signature.safety_signature_back.app.user.dto.UserMasterDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
@Setter
@Getter
@Schema(description = "게시판 테이블")
@Data
@EqualsAndHashCode(callSuper = true,of="id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BulletinBoardMasterDTO extends AbstractAuditingDTO<String> implements Serializable {
    @Size(max = 36)
    @Schema(description = "게시글 고유 ID" ,requiredMode= Schema.RequiredMode.REQUIRED)
    private String id;

    @Size(max =1000)
    @NotNull
    @Schema(description = "게시글 제목")
    private String boardTitle;

    @Size(max =5000)
    @Schema(description = "게시글 내용")
    private String boardContents;


    @Schema(description = "첨부파일 유무")
    private Boolean attachYn;

    @Size(max = 36)
    @Schema(description = "게시글 오너 ID")
    private String userMasterId;

    @Schema(description = "게시글 오너 ID")
    private UserMasterDTO userMasterDTO;
}

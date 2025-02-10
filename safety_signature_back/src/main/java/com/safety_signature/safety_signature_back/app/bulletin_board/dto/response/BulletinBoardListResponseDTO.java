package com.safety_signature.safety_signature_back.app.bulletin_board.dto.response;

import com.fasterxml.jackson.annotation.JsonView;
import com.safety_signature.safety_signature_back.app.bulletin_board.dto.BulletinBoardMasterDTO;
import com.safety_signature.safety_signature_back.app.common.dto.View;
import com.safety_signature.safety_signature_back.config.Partial;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BulletinBoardListResponseDTO extends BulletinBoardResponseBaseDTO{

    List<BulletinBoardMasterDTO>data;

    long totalCount;
}

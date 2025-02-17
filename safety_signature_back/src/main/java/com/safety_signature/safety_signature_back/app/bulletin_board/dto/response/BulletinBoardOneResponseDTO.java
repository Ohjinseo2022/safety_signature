package com.safety_signature.safety_signature_back.app.bulletin_board.dto.response;

import com.safety_signature.safety_signature_back.app.bulletin_board.dto.custom.BulletinBoardMasterCustomDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BulletinBoardOneResponseDTO  extends BulletinBoardResponseBaseDTO{
    private BulletinBoardMasterCustomDTO data;
}

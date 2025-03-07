package com.safety_signature.safety_signature_back.app.bulletin_board.dto.response;


import com.safety_signature.safety_signature_back.app.bulletin_board.dto.custom.BulletinBoardMasterCustomDTO;
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

    private List<BulletinBoardMasterCustomDTO> data;

    private long totalCount; // 필요 없을지도 몰라용

}



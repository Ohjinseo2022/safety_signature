package com.safety_signature.safety_signature_back.app.approve_master.dto.response;

import com.safety_signature.safety_signature_back.app.approve_master.dto.ApproveMasterDTO;
import com.safety_signature.safety_signature_back.app.common.dto.AttachDocMasterDTO;
import com.safety_signature.safety_signature_back.utils.DateUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApproveMasterCustomDTO extends ApproveMasterDTO {
    @Schema(description = "최소 생성 일시 문자열 포멧")
    private String createdDateFormat;
//    @Schema(description = "업체 명")
//    private String companyName;
//
//    @Schema(description = "공사 종목 명")
//    private String constructionBusiness;

    public static ApproveMasterCustomDTO from(ApproveMasterDTO approveMasterDTO) {
        ApproveMasterCustomDTO approveMasterCustomDTO = new ApproveMasterCustomDTO();
        BeanUtils.copyProperties(approveMasterDTO, approveMasterCustomDTO);
        approveMasterCustomDTO.setCreatedDateFormat(DateUtil.instantToStringDate(approveMasterCustomDTO.getCreatedDate(), "yyyy-MM-dd hh:mm:ss"));
        return approveMasterCustomDTO;
    }

}

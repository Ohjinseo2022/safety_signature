package com.safety_signature.safety_signature_back.app.common.mapper;

import com.safety_signature.safety_signature_back.app.common.domain.AttachDocMaster;
import com.safety_signature.safety_signature_back.app.common.dto.AttachDocMasterDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface AttachDocMasterMapper extends EntityMapper<AttachDocMasterDTO, AttachDocMaster> {

    @Override
    AttachDocMasterDTO toDto(AttachDocMaster entity);

    @Override
    AttachDocMaster toEntity(AttachDocMasterDTO dto);

}

package com.safety_signature.safety_signature_back.app.approve_master.mapper;

import com.safety_signature.safety_signature_back.app.approve_master.domain.ApproveMaster;
import com.safety_signature.safety_signature_back.app.approve_master.dto.ApproveMasterDTO;
import com.safety_signature.safety_signature_back.app.common.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ApproveMasterMapper extends EntityMapper<ApproveMasterDTO, ApproveMaster> {

    @Override
    ApproveMasterDTO toDto(ApproveMaster entity);

    @Override
    ApproveMaster toEntity(ApproveMasterDTO dto);
}

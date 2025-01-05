package com.safety_signature.safety_signature_back.app.company.mapper;

import com.safety_signature.safety_signature_back.app.common.mapper.EntityMapper;
import com.safety_signature.safety_signature_back.app.company.domain.CompanyMaster;
import com.safety_signature.safety_signature_back.app.company.dto.CompanyMasterDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface CompanyMasterMapper extends EntityMapper<CompanyMasterDTO, CompanyMaster> {

    @Override
    CompanyMasterDTO toDto(CompanyMaster companyMaster);

    @Override
    CompanyMaster toEntity(CompanyMasterDTO companyMasterDTO);
}

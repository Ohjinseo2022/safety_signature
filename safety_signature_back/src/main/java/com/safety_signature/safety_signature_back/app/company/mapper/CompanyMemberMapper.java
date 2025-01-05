package com.safety_signature.safety_signature_back.app.company.mapper;


import com.safety_signature.safety_signature_back.app.common.mapper.EntityMapper;
import com.safety_signature.safety_signature_back.app.company.domain.CompanyMaster;
import com.safety_signature.safety_signature_back.app.company.domain.CompanyMember;
import com.safety_signature.safety_signature_back.app.company.dto.CompanyMasterDTO;
import com.safety_signature.safety_signature_back.app.company.dto.CompanyMemberDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface CompanyMemberMapper extends EntityMapper<CompanyMemberDTO, CompanyMember> {
    @Override
    @Mapping(target = "userMasterDTO" , source = "userMaster")
    @Mapping(target = "companyMasterDTO" , source = "companyMaster")
    CompanyMemberDTO toDto(CompanyMember companyMember);

    @Override
    @Mapping(target="userMaster" , source = "userMasterDTO")
    @Mapping(target="companyMaster" , source = "companyMasterDTO")
    CompanyMember toEntity(CompanyMemberDTO companyMemberDTO);

}

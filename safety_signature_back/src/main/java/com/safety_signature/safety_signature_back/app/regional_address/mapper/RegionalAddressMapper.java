package com.safety_signature.safety_signature_back.app.regional_address.mapper;

import com.safety_signature.safety_signature_back.app.common.mapper.EntityMapper;
import com.safety_signature.safety_signature_back.app.regional_address.domain.RegionalAddress;
import com.safety_signature.safety_signature_back.app.regional_address.dto.RegionalAddressDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface RegionalAddressMapper extends EntityMapper<RegionalAddressDTO, RegionalAddress> {
    @Override
    @Mapping(target ="companyMasterDTO" , source="companyMaster")
    RegionalAddressDTO toDto(RegionalAddress entity);

    @Override
    @Mapping(target ="companyMaster" , source="companyMasterDTO")
    RegionalAddress toEntity(RegionalAddressDTO dto);
}

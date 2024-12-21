package com.safety_signature.safety_signature_back.app.user.mapper;
import com.safety_signature.safety_signature_back.app.common.mapper.EntityMapper;
import com.safety_signature.safety_signature_back.app.user.domain.UserMaster;
import com.safety_signature.safety_signature_back.app.user.dto.UserMasterDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface UserMaterMapper extends EntityMapper<UserMasterDTO, UserMaster> {
    @Override
    @Mapping(target = "name",source="userName")
    UserMasterDTO toDto(UserMaster entity);

    @Override
    @Mapping(target = "userName",source="name")
    UserMaster toEntity(UserMasterDTO dto);
}

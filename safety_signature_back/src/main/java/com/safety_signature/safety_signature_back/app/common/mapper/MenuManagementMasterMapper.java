package com.safety_signature.safety_signature_back.app.common.mapper;

import com.safety_signature.safety_signature_back.app.common.domain.MenuManagementMaster;
import com.safety_signature.safety_signature_back.app.common.dto.MenuManagementMasterDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MenuManagementMasterMapper extends  EntityMapper<MenuManagementMasterDTO, MenuManagementMaster>{

    @Override
    MenuManagementMaster toEntity(MenuManagementMasterDTO dto);

    @Override
    MenuManagementMasterDTO toDto(MenuManagementMaster entity);
}

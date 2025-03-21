package com.safety_signature.safety_signature_back.app.bulletin_board.mapper;

import com.safety_signature.safety_signature_back.app.bulletin_board.domain.BulletinBoardMaster;
import com.safety_signature.safety_signature_back.app.bulletin_board.dto.BulletinBoardMasterDTO;
import com.safety_signature.safety_signature_back.app.common.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface BulletinBoardMasterMapper extends EntityMapper<BulletinBoardMasterDTO, BulletinBoardMaster> {
    @Override
    @Mapping(target = "userMasterDTO",source = "userMaster")
//    @Mapping(target = "userMasterDTO.name",source = "userMaster.userName")
    BulletinBoardMasterDTO toDto(BulletinBoardMaster entity);

    @Override
    @Mapping(target="userMaster",source = "userMasterDTO")
//    @Mapping(target="userMaster.userName",source = "userMasterDTO.name")
    BulletinBoardMaster toEntity(BulletinBoardMasterDTO dto);
}

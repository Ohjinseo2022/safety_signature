package com.safety_signature.safety_signature_back.app.bulletin_board.mapper;

import com.safety_signature.safety_signature_back.app.bulletin_board.domain.BulletinBoardAttachInfo;
import com.safety_signature.safety_signature_back.app.bulletin_board.dto.BulletinBoardAttachInfoDTO;
import com.safety_signature.safety_signature_back.app.common.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface BulletinBoardAttachInfoMapper extends EntityMapper<BulletinBoardAttachInfoDTO, BulletinBoardAttachInfo> {

    @Override
    @Mapping(target = "userMasterDTO",source = "userMaster")
    BulletinBoardAttachInfoDTO toDto(BulletinBoardAttachInfo bbAttachInfo);

    @Override
    @Mapping(target="userMaster",source = "userMasterDTO")
    BulletinBoardAttachInfo toEntity(BulletinBoardAttachInfoDTO bbAttachInfoDTO);
}

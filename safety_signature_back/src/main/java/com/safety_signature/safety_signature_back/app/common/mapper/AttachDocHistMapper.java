package com.safety_signature.safety_signature_back.app.common.mapper;

import com.safety_signature.safety_signature_back.app.common.domain.AttachDocHist;
import com.safety_signature.safety_signature_back.app.common.dto.AttachDocHistDTO;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper for the entity {@link AttachDocHist} and its DTO {@link AttachDocHistDTO}.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AttachDocHistMapper extends EntityMapper<AttachDocHistDTO, AttachDocHist>{

    @Override
    AttachDocHistDTO toDto(AttachDocHist entity);

    @Override
    AttachDocHist toEntity(AttachDocHistDTO dto);
}

package com.safety_signature.safety_signature_back.app.common.mapper;

import com.safety_signature.safety_signature_back.app.common.domain.AttachDocPartition;
import com.safety_signature.safety_signature_back.app.common.dto.AttachDocPartitionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper for the entity {@link com.safety_signature.safety_signature_back.app.common.domain.AttachDocPartition} and its DTO {@link AttachDocPartitionDTO}.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AttachDocPartitionMapper extends EntityMapper<AttachDocPartitionDTO, AttachDocPartition>{

    @Override
    AttachDocPartition toEntity(AttachDocPartitionDTO dto);

    @Override
    AttachDocPartitionDTO toDto(AttachDocPartition entity);

}

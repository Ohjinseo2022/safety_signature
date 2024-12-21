package com.safety_signature.safety_signature_back.app.common.mapper;

import com.safety_signature.safety_signature_back.app.common.dto.AbstractAuditingDTO;
import com.safety_signature.safety_signature_back.utils.common.TsidUtil;
import org.mapstruct.BeanMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.util.StringUtils;

import javax.inject.Named;
import java.util.List;

public interface EntityMapper<D,E> {
    E toEntity(D dto);
    D toDto(E entity);

    List<E> toEntityList(List<D> dtoList);

    List<E> toDtoList(List<E> entityList);

    @Named("partialUpdate")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(@MappingTarget  E entity,D dto);

    @BeforeMapping
    default void generateId(D dto){
        if(dto == null) return;
        // ID 정보를 자동으로 만들어주는 설정
        if(dto instanceof AbstractAuditingDTO<?>){
            AbstractAuditingDTO auditingDTO = (AbstractAuditingDTO) dto;
            if(auditingDTO.getId() == null || !StringUtils.hasText(auditingDTO.getId().toString())){
                auditingDTO.setId(TsidUtil.TSID_FACTORY.create().toString());
            }
        }
    }
}

package com.safety_signature.safety_signature_back.app.approve_master.service.impl;

import com.safety_signature.safety_signature_back.app.approve_master.domain.ApproveMaster;
import com.safety_signature.safety_signature_back.app.approve_master.dto.ApproveMasterDTO;
import com.safety_signature.safety_signature_back.app.approve_master.mapper.ApproveMasterMapper;
import com.safety_signature.safety_signature_back.app.approve_master.repository.ApproveMasterRepository;
import com.safety_signature.safety_signature_back.app.approve_master.service.ApproveMasterService;
import com.safety_signature.safety_signature_back.app.user.dto.UserMasterDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ApproveMasterServiceImpl implements ApproveMasterService {
    private final Logger log = LoggerFactory.getLogger(ApproveMasterServiceImpl.class);
    private final ApproveMasterRepository approveMasterRepository;
    private final ApproveMasterMapper approveMasterMapper;

    public ApproveMasterServiceImpl(ApproveMasterRepository approveMasterRepository, ApproveMasterMapper approveMasterMapper) {
        this.approveMasterRepository = approveMasterRepository;
        this.approveMasterMapper = approveMasterMapper;
    }

    @Override
    public ApproveMasterDTO addApproveMaster(String bulletinBoardId, UserMasterDTO userMasterDTO) {
        ApproveMasterDTO approveMasterDTO = ApproveMasterDTO.builder()
                .userMasterId(userMasterDTO.getId())
                .userName(userMasterDTO.getName())
                .attachDocId(userMasterDTO.getSignatureDocId())
                .bulletinBoardId(bulletinBoardId)
                .approveStatus("approve")
                .build();
        return this.save(approveMasterDTO);
    }

    @Override
    public Optional<ApproveMasterDTO> partialUpdate(ApproveMasterDTO approveMasterDTO) {
        log.debug("Request partialUpdate(ApproveMasterDTO : {}", approveMasterDTO);
        return approveMasterRepository.findById(approveMasterDTO.getId())
                .map(existingApproveMaster->{
                    approveMasterMapper.partialUpdate(existingApproveMaster,approveMasterDTO);
                    return existingApproveMaster;
                })
                .map(approveMasterRepository::save)
                .map(approveMasterMapper::toDto);
    }

    @Override
    public ApproveMasterDTO save(ApproveMasterDTO approveMasterDTO) {
        log.debug("Request to save ApproveMasterDTO : {}", approveMasterDTO);
        return approveMasterMapper.toDto(approveMasterRepository.save(approveMasterMapper.toEntity(approveMasterDTO)));
    }
}

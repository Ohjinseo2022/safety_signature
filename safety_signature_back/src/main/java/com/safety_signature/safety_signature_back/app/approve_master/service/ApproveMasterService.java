package com.safety_signature.safety_signature_back.app.approve_master.service;

import com.safety_signature.safety_signature_back.app.approve_master.dto.ApproveMasterDTO;
import com.safety_signature.safety_signature_back.app.user.dto.UserMasterDTO;

import java.util.Optional;

public interface ApproveMasterService {
    ApproveMasterDTO addApproveMaster(String bulletinBoardId, UserMasterDTO userMasterDTO);
    Optional<ApproveMasterDTO> partialUpdate(ApproveMasterDTO approveMasterDTO);
    ApproveMasterDTO save(ApproveMasterDTO approveMasterDTO);
}

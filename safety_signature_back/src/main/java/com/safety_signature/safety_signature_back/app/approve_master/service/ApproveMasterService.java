package com.safety_signature.safety_signature_back.app.approve_master.service;

import com.safety_signature.safety_signature_back.app.approve_master.dto.ApproveMasterDTO;
import com.safety_signature.safety_signature_back.app.approve_master.dto.response.ApproveMasterCustomDTO;
import com.safety_signature.safety_signature_back.app.bulletin_board.dto.BulletinBoardMasterDTO;
import com.safety_signature.safety_signature_back.app.user.dto.UserMasterDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ApproveMasterService {
    ApproveMasterDTO addApproveMaster(String bulletinBoardId, UserMasterDTO userMasterDTO);
    Page<ApproveMasterCustomDTO> getAllApproveMasters(BulletinBoardMasterDTO bulletinBoardMasterDTO , Pageable pageable);
    Optional<ApproveMasterDTO> partialUpdate(ApproveMasterDTO approveMasterDTO);
    ApproveMasterDTO save(ApproveMasterDTO approveMasterDTO);
    List<ApproveMasterDTO> userMasterByExistingApproveMastersOnTheBulletinBoard (String bulletinBoardId , String userMasterId);
    List<ApproveMasterCustomDTO> existingApproveMastersOnTheBulletinBoard (BulletinBoardMasterDTO bulletinBoardMasterDTO);
}

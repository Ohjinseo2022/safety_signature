package com.safety_signature.safety_signature_back.app.approve_master.service;

import com.safety_signature.safety_signature_back.app.approve_master.dto.ApproveMasterDTO;
import com.safety_signature.safety_signature_back.app.approve_master.dto.response.ApproveMasterCustomDTO;
import com.safety_signature.safety_signature_back.app.approve_master.dto.response.ApproveMasterListCustomDTO;
import com.safety_signature.safety_signature_back.app.bulletin_board.dto.BulletinBoardMasterDTO;
import com.safety_signature.safety_signature_back.app.common.dto.util.InfiniteScrollResponseDTO;
import com.safety_signature.safety_signature_back.app.user.dto.UserMasterDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public interface ApproveMasterService {
    ApproveMasterDTO addApproveMaster(String bulletinBoardId, UserMasterDTO userMasterDTO);
    Page<ApproveMasterCustomDTO> getAllApproveMasters(BulletinBoardMasterDTO bulletinBoardMasterDTO , Pageable pageable);
    Optional<ApproveMasterDTO> partialUpdate(ApproveMasterDTO approveMasterDTO);
    ApproveMasterDTO save(ApproveMasterDTO approveMasterDTO);
    List<ApproveMasterDTO> userMasterByExistingApproveMastersOnTheBulletinBoard (String bulletinBoardId , String userMasterId);
    List<ApproveMasterCustomDTO> existingApproveMastersOnTheBulletinBoard (BulletinBoardMasterDTO bulletinBoardMasterDTO);
    byte[] createApproveMasterDocx(BulletinBoardMasterDTO bulletinBoardMasterDTO , List<ApproveMasterCustomDTO> approveList) throws IOException;
    ApproveMasterListCustomDTO getInfiniteScrollList(BulletinBoardMasterDTO bulletinBoardMasterDTO, String bulletinBoardId , Pageable pageable) ;
}

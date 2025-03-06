package com.safety_signature.safety_signature_back.app.bulletin_board.service;

import com.safety_signature.safety_signature_back.app.bulletin_board.dto.BulletinBoardMasterDTO;
import com.safety_signature.safety_signature_back.app.bulletin_board.dto.custom.BulletinBoardMasterCustomDTO;
import com.safety_signature.safety_signature_back.app.bulletin_board.dto.request.BulletinBoardRegistrationRequestDTO;
import com.safety_signature.safety_signature_back.app.common.dto.util.InfiniteScrollResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface BulletinBoardMasterService {
    BulletinBoardMasterDTO saveBulletinBoardMaster(BulletinBoardRegistrationRequestDTO registrationRequestDTO , List<MultipartFile> files, String userEmail);
    BulletinBoardMasterDTO save(BulletinBoardMasterDTO bulletinBoardMasterDTO);
    Page<BulletinBoardMasterCustomDTO> getBulletinBoardMasterSearchConditionList(String boardTitle, String createdBy, String startDate, String endDate, String ownerId, Pageable pageable);
    BulletinBoardMasterCustomDTO getBulletinBoardMasterCustomDTO(String bulletinBoardId);
    BulletinBoardMasterDTO getBulletinBoardMasterDTO(String bulletinBoardId);
    InfiniteScrollResponseDTO<BulletinBoardMasterCustomDTO> getInfiniteScrollData(Optional<String> cursor, Pageable pageable);
    BulletinBoardMasterDTO partialUpdate(BulletinBoardMasterDTO bulletinBoardMasterDTO);
}

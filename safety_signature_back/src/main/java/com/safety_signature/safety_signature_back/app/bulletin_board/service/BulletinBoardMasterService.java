package com.safety_signature.safety_signature_back.app.bulletin_board.service;

import com.safety_signature.safety_signature_back.app.bulletin_board.dto.BulletinBoardMasterDTO;
import com.safety_signature.safety_signature_back.app.bulletin_board.dto.request.BulletinBoardRegistrationRequestDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface BulletinBoardMasterService {
    BulletinBoardMasterDTO saveBulletinBoardMaster(BulletinBoardRegistrationRequestDTO registrationRequestDTO , List<MultipartFile> files, String userEmail);
    BulletinBoardMasterDTO save(BulletinBoardMasterDTO bulletinBoardMasterDTO);
}

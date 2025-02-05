package com.safety_signature.safety_signature_back.app.bulletin_board.service.impl;

import com.safety_signature.safety_signature_back.app.bulletin_board.dto.BulletinBoardMasterDTO;
import com.safety_signature.safety_signature_back.app.bulletin_board.dto.request.BulletinBoardRegistrationRequestDTO;
import com.safety_signature.safety_signature_back.app.bulletin_board.service.BulletinBoardMasterService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BulletinBoardMasterServiceImpl implements BulletinBoardMasterService {
    /**
     * @param registrationRequestDTO
     * @param files
     * @param userEmail
     * @return 전자결제 문서 MinIo 저장 및 게시글 저장 처리 후 Optional<BulletinBoardMasterDTO> 리턴
     */
    @Override
    public Optional<BulletinBoardMasterDTO> saveBulletinBoardMaster(BulletinBoardRegistrationRequestDTO registrationRequestDTO, List<MultipartFile> files , String userEmail) {
        /**
         * 1.회원 ID 정보 필요함
         * 2.권한체크
         * 3.첨부파일 저장 (리스트 형태)
         * 4.첨부파일 저장 성공시 게시글 저장 처리
         */

        return Optional.empty();
    }
}

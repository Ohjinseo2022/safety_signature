package com.safety_signature.safety_signature_back.app.bulletin_board.resource;

import com.safety_signature.safety_signature_back.app.bulletin_board.dto.BulletinBoardMasterDTO;
import com.safety_signature.safety_signature_back.app.bulletin_board.dto.request.BulletinBoardRegistrationRequestDTO;
import com.safety_signature.safety_signature_back.app.bulletin_board.dto.response.BulletinBoardRegistrationResponseMessageDTO;
import com.safety_signature.safety_signature_back.app.bulletin_board.service.BulletinBoardMasterService;
import com.safety_signature.safety_signature_back.config.Constants;
import com.safety_signature.safety_signature_back.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Tag(name="전자결제 게시판 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/bulletin-board")
public class BulletinBoardMasterResource {
    private final Logger log = LoggerFactory.getLogger(BulletinBoardMasterResource.class);
    private final BulletinBoardMasterService bulletinBoardMasterService;
    @Operation(summary = "전자 결제 문서 등록")
    @PostMapping("/registration")
        public ResponseEntity<BulletinBoardRegistrationResponseMessageDTO> registration(
                @RequestPart("boardData") BulletinBoardRegistrationRequestDTO bulletinBoardRegistrationRequestDTO,
                @RequestPart(value = "files", required = false) List<MultipartFile> files) throws Exception{

        String userId = SecurityUtils.getCurrentUserLogin().orElse(null);
        // 유저정보가 없다면 401 코드를 전송시켜서, 토큰 갱신 API 호출을 유도함.
        if (userId == null) {
            BulletinBoardRegistrationResponseMessageDTO result = BulletinBoardRegistrationResponseMessageDTO.builder().httpStatus(HttpStatus.UNAUTHORIZED).message("UNAUTHORIZED").build();
            return ResponseEntity.status(result.getHttpStatus()).body(result);
        }
        log.info("userId:{}", userId);
        log.info("bulletinBoardRegistrationRequestDTO:{}", bulletinBoardRegistrationRequestDTO);
        log.info("files:{}", files);
        Optional<BulletinBoardMasterDTO> bulletinBoardMasterDTO = bulletinBoardMasterService.saveBulletinBoardMaster(bulletinBoardRegistrationRequestDTO , files , userId);
        if(bulletinBoardMasterDTO.isPresent()){
            BulletinBoardRegistrationResponseMessageDTO result = BulletinBoardRegistrationResponseMessageDTO.builder().httpStatus(HttpStatus.OK).message("SUCCESSFUL").build();
            return ResponseEntity.status(result.getHttpStatus()).body(result);
        }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BulletinBoardRegistrationResponseMessageDTO.builder().httpStatus(HttpStatus.INTERNAL_SERVER_ERROR).build());

    }


}

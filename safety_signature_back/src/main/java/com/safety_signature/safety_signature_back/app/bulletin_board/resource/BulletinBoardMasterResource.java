package com.safety_signature.safety_signature_back.app.bulletin_board.resource;

import com.safety_signature.safety_signature_back.app.bulletin_board.dto.BulletinBoardMasterDTO;
import com.safety_signature.safety_signature_back.app.bulletin_board.dto.request.BulletinBoardRegistrationRequestDTO;
import com.safety_signature.safety_signature_back.config.Constants;
import com.safety_signature.safety_signature_back.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name="전자결제 게시판 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/bulletin-board")
public class BulletinBoardMasterResource {
    private final Logger log = LoggerFactory.getLogger(BulletinBoardMasterResource.class);
    @Operation(summary = "전자 결제 문서 등록")
    @PostMapping("/registration")
        public ResponseEntity<?> registration(@RequestPart("boardData") BulletinBoardRegistrationRequestDTO bulletinBoardRegistrationRequestDTO,
                                              @RequestPart(value = "files", required = false) List<MultipartFile> files) throws Exception{
        String userId = SecurityUtils.getCurrentUserLogin().orElse(Constants.SYSTEM);
        log.info("bulletinBoardRegistrationRequestDTO:{}", bulletinBoardRegistrationRequestDTO);
        log.info("files:{}", files);
        BulletinBoardMasterDTO bulletinBoardMasterDTO = new BulletinBoardMasterDTO();
            return null;
        }

}

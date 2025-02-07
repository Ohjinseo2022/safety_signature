package com.safety_signature.safety_signature_back.app.bulletin_board.resource;

import com.safety_signature.safety_signature_back.app.bulletin_board.dto.BulletinBoardMasterDTO;
import com.safety_signature.safety_signature_back.app.bulletin_board.dto.request.BulletinBoardRegistrationRequestDTO;
import com.safety_signature.safety_signature_back.app.bulletin_board.dto.response.BulletinBoardResponseBaseDTO;
import com.safety_signature.safety_signature_back.app.bulletin_board.dto.response.BulletinBoardResponseMessageDTO;
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
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Tag(name="전자결제 게시판 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/bulletin-board/registration")
public class BulletinBoardMasterResource {
    private final Logger log = LoggerFactory.getLogger(BulletinBoardMasterResource.class);
    private final BulletinBoardMasterService bulletinBoardMasterService;
    @Operation(summary = "전자 결제 문서 등록")
    @PostMapping("")
        public ResponseEntity<BulletinBoardResponseBaseDTO> registration(
                @RequestPart("boardData") BulletinBoardRegistrationRequestDTO bulletinBoardRegistrationRequestDTO,
                @RequestPart(value = "files", required = false) List<MultipartFile> files) throws Exception{
        String userEmail = SecurityUtils.getCurrentUserLogin().orElse(null);
        // 유저정보가 없다면 401 코드를 전송시켜서, 토큰 갱신 API 호출을 유도함.
        if (Constants.ANONYMOUSUSER.equals(userEmail)|| userEmail ==null) {
            BulletinBoardResponseMessageDTO result = BulletinBoardResponseMessageDTO.builder().httpStatus(HttpStatus.UNAUTHORIZED).message("UNAUTHORIZED").build();
            return ResponseEntity.status(result.getHttpStatus()).body(result);
        }
        log.info("userEmail:{}", userEmail);
        log.info("bulletinBoardRegistrationRequestDTO:{}", bulletinBoardRegistrationRequestDTO);
        log.info("files:{}", files);
        BulletinBoardMasterDTO bulletinBoardMasterDTO = bulletinBoardMasterService.saveBulletinBoardMaster(bulletinBoardRegistrationRequestDTO , files , userEmail);
        if(!ObjectUtils.isEmpty(bulletinBoardMasterDTO)){
            BulletinBoardResponseMessageDTO result = BulletinBoardResponseMessageDTO.builder().httpStatus(HttpStatus.OK).message("SUCCESSFUL").build();
            return ResponseEntity.status(result.getHttpStatus()).body(result);
        }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BulletinBoardResponseMessageDTO.builder().httpStatus(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    @Operation(summary = "전자 결제 문서 관리자 리스트 조회")
    @GetMapping("/list-for-administrators")
    public ResponseEntity<BulletinBoardResponseBaseDTO> listForAdministrators(
          @RequestParam(name = "title", required = false) String title,
          @RequestParam(name = "createdBy", required = false) String createdBy,
          @RequestParam(name = "startDate", required = false) String startDate,
          @RequestParam(name = "endDate", required = false) String endDate,
          Pageable pageable)throws Exception{

        String userEmail = SecurityUtils.getCurrentUserLogin().orElse(null);
        // 유저정보가 없다면 401 코드를 전송시켜서, 토큰 갱신 API 호출을 유도함.
        if (Constants.ANONYMOUSUSER.equals(userEmail)|| userEmail ==null) {
            BulletinBoardResponseMessageDTO result = BulletinBoardResponseMessageDTO.builder().httpStatus(HttpStatus.UNAUTHORIZED).message("UNAUTHORIZED").build();
            return ResponseEntity.status(result.getHttpStatus()).body(result);
        }
        /**
         * 1. 검색조건
         *      - 게시글 제목
         *      - 작성자
         *      - 기간
         *      - 내가쓴글
         *      - 로그인한 권한 체크
         *      - 페이지네이션
         *
         */
        return null;

    }

}

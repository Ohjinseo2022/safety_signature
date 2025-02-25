package com.safety_signature.safety_signature_back.app.approve_master.resource;

import com.safety_signature.safety_signature_back.app.approve_master.dto.ApproveMasterDTO;
import com.safety_signature.safety_signature_back.app.approve_master.dto.request.ApproveCompletedSignatureRequestDTO;
import com.safety_signature.safety_signature_back.app.approve_master.dto.response.ApproveMasterCustomDTO;
import com.safety_signature.safety_signature_back.app.approve_master.dto.response.ApproveMasterListDTO;
import com.safety_signature.safety_signature_back.app.approve_master.dto.response.ApproveMasterMessageDTO;
import com.safety_signature.safety_signature_back.app.approve_master.service.ApproveMasterService;
import com.safety_signature.safety_signature_back.app.bulletin_board.dto.BulletinBoardMasterDTO;
import com.safety_signature.safety_signature_back.app.bulletin_board.dto.response.BulletinBoardResponseMessageDTO;
import com.safety_signature.safety_signature_back.app.bulletin_board.service.BulletinBoardMasterService;
import com.safety_signature.safety_signature_back.app.common.dto.ResponseDTO;
import com.safety_signature.safety_signature_back.app.user.service.UserMasterService;
import com.safety_signature.safety_signature_back.config.Constants;
import com.safety_signature.safety_signature_back.utils.PaginationUtil;
import com.safety_signature.safety_signature_back.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Tag(name = "전자결제 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/approve")
public class ApproveMasterResource {
    private final Logger log = LoggerFactory.getLogger(ApproveMasterResource.class);
    private final ApproveMasterService approveMasterService;
    private final BulletinBoardMasterService bulletinBoardMasterService;
    private final UserMasterService userMasterService;

    @Operation(summary = "전자 결제 내역 조회")
    @GetMapping(value = "/completed-list/{bulletinBoardId}")
    public ResponseEntity<ResponseDTO> getCompletedList( @PathVariable String bulletinBoardId, Pageable pageable) {
        String userEmail = SecurityUtils.getCurrentUserLogin().orElse(null);
        // 유저정보가 없다면 401 코드를 전송시켜서, 토큰 갱신 API 호출을 유도함.
        if (Constants.ANONYMOUSUSER.equals(userEmail)|| userEmail ==null) {
            ApproveMasterMessageDTO result = ApproveMasterMessageDTO.builder().httpStatus(HttpStatus.UNAUTHORIZED).message("UNAUTHORIZED").build();
            return ResponseEntity.status(result.getHttpStatus()).body(result);
        }
        BulletinBoardMasterDTO bulletinBoardMasterDTO =  bulletinBoardMasterService.getBulletinBoardMasterDTO(bulletinBoardId);
        if(ObjectUtils.isEmpty(bulletinBoardMasterDTO)){
            ApproveMasterMessageDTO masterMessageDTO = ApproveMasterMessageDTO.builder().message("존재하지 않는 전자 결제 게시글 입니다.").httpStatus(HttpStatus.INTERNAL_SERVER_ERROR).build();
            return ResponseEntity.status(masterMessageDTO.getHttpStatus()).body(masterMessageDTO);
        }
        // =========================================================================================================
        // 페이징 설정
        // =========================================================================================================
        if(ObjectUtils.isEmpty(pageable)) {
            pageable =  PageRequest.of(0, 10, Sort.by(Sort.Order.desc("createdDate")));
        } else {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Order.desc("createdDate")));
        }
        Page<ApproveMasterCustomDTO> result =  approveMasterService.getAllApproveMasters(bulletinBoardMasterDTO, pageable);
        HttpHeaders headers =  PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), result);
        return ResponseEntity.ok().headers(headers).body(ApproveMasterListDTO.builder().data(result.getContent()).build());
    }

    @Operation(summary = "전자 결제 사인 처리")
    @PostMapping(value = "/completed-signature")
    public ResponseEntity<ResponseDTO> postCompletedSignature( @RequestBody @Valid ApproveCompletedSignatureRequestDTO approveCompletedSignatureRequestDTO){
        String userEmail = SecurityUtils.getCurrentUserLogin().orElse(null);
        /**
         * 1. 기존 게시글 조회
         * 2. 서명 가능한 상태 체크
         * 3. 중복 결제 막기
         */
        // 유저정보가 없다면 401 코드를 전송시켜서, 토큰 갱신 API 호출을 유도함.
        if (Constants.ANONYMOUSUSER.equals(userEmail)|| userEmail ==null) {
            ApproveMasterMessageDTO result = ApproveMasterMessageDTO.builder().httpStatus(HttpStatus.UNAUTHORIZED).message("UNAUTHORIZED").build();
            return ResponseEntity.status(result.getHttpStatus()).body(result);
        }
        BulletinBoardMasterDTO bulletinBoardMasterDTO =  bulletinBoardMasterService.getBulletinBoardMasterDTO(approveCompletedSignatureRequestDTO.getBulletinBoardId());
        if(ObjectUtils.isEmpty(bulletinBoardMasterDTO)){
            ApproveMasterMessageDTO masterMessageDTO = ApproveMasterMessageDTO.builder().message("존재하지 않는 전자 결제 게시글 입니다.").httpStatus(HttpStatus.INTERNAL_SERVER_ERROR).build();
            return ResponseEntity.status(masterMessageDTO.getHttpStatus()).body(masterMessageDTO);
        }
        ApproveMasterDTO result = approveMasterService.addApproveMaster(approveCompletedSignatureRequestDTO.getBulletinBoardId(),userMasterService.getUserMasterByEmail(userEmail));
        if(ObjectUtils.isEmpty(result)){
            ApproveMasterMessageDTO masterMessageDTO = ApproveMasterMessageDTO.builder().message("전자 결제 승인 처리에 실패 했습니다.").httpStatus(HttpStatus.INTERNAL_SERVER_ERROR).build();
            return ResponseEntity.status(masterMessageDTO.getHttpStatus()).body(masterMessageDTO);
        }
        ApproveMasterMessageDTO masterMessageDTO = ApproveMasterMessageDTO.builder().message("전자 결제 승인 완료").httpStatus(HttpStatus.OK).build();
        return ResponseEntity.status(masterMessageDTO.getHttpStatus()).body(masterMessageDTO);
    }
}

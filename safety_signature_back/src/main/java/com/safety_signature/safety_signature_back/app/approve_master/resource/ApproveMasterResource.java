package com.safety_signature.safety_signature_back.app.approve_master.resource;

import com.safety_signature.safety_signature_back.app.approve_master.service.ApproveMasterService;
import com.safety_signature.safety_signature_back.app.bulletin_board.dto.response.BulletinBoardResponseMessageDTO;
import com.safety_signature.safety_signature_back.app.user.service.UserMasterService;
import com.safety_signature.safety_signature_back.config.Constants;
import com.safety_signature.safety_signature_back.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

@Tag(name = "전자결제 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/approve")
public class ApproveMasterResource {
    private final Logger log = LoggerFactory.getLogger(ApproveMasterResource.class);
    private final ApproveMasterService approveMasterService;
    private final UserMasterService userMasterService;

    @Operation(summary = "전자 결제 내역 조회")
    @GetMapping(value = "/completed-list/{bulletinBoardId}")
    public ResponseEntity<?> getCompletedList(HttpServletRequest request, @PathVariable String bulletinBoardId, Pageable pageable) {
        String userEmail = SecurityUtils.getCurrentUserLogin().orElse(null);
        // =========================================================================================================
        // 페이징 설정
        // =========================================================================================================
        if(ObjectUtils.isEmpty(pageable)) {
            pageable =  PageRequest.of(0, 10, Sort.by(Sort.Order.desc("createdDate")));
        } else {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Order.desc("createdDate")));
        }

        return null;
    }

    @Operation(summary = "전자 결제 사인 처리")
    @PostMapping(value = "/completed-signature")
    public ResponseEntity<?> postCompletedSignature(HttpServletRequest request){
        String userEmail = SecurityUtils.getCurrentUserLogin().orElse(null);
        /**
         * 1. 기존 게시글 조회
         * 2. 서명 가능한 상태 체크
         * 3. 저장
         */
        return null;
    }
}

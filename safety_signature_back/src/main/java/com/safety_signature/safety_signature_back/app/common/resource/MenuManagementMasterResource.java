package com.safety_signature.safety_signature_back.app.common.resource;

import com.safety_signature.safety_signature_back.app.bulletin_board.dto.response.BulletinBoardResponseMessageDTO;
import com.safety_signature.safety_signature_back.app.common.dto.ResponseDTO;
import com.safety_signature.safety_signature_back.app.common.dto.ResponseMessageDTO;
import com.safety_signature.safety_signature_back.config.Constants;
import com.safety_signature.safety_signature_back.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "메뉴관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/menu")
public class MenuManagementMasterResource {
    private final Logger log = LoggerFactory.getLogger(MenuManagementMasterResource.class);

    @Operation(summary = "권한 별 메뉴 조회")
    @GetMapping("/auth-menu-list")
    public ResponseEntity<ResponseDTO> getAuthMenuList() {

        String userEmail = SecurityUtils.getCurrentUserLogin().orElse(null);
        // 유저정보가 없다면 401 코드를 전송시켜서, 토큰 갱신 API 호출을 유도함.
        if (Constants.ANONYMOUSUSER.equals(userEmail)|| userEmail ==null) {
            log.info("/auth-menu-list UNAUTHORIZED");
            ResponseMessageDTO result = ResponseMessageDTO.builder().httpStatus(HttpStatus.UNAUTHORIZED).message("UNAUTHORIZED").build();
            return ResponseEntity.status(result.getHttpStatus()).body(null);
        }
        log.info("/auth-menu-list start userEmail:{}",userEmail);

        return null;
    }
}

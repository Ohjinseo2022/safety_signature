package com.safety_signature.safety_signature_back.app.user.resource;

import com.safety_signature.safety_signature_back.app.auth.service.AuthTransactionalService;
import com.safety_signature.safety_signature_back.app.common.dto.View;
import com.safety_signature.safety_signature_back.app.user.dto.UserMasterDTO;
import com.safety_signature.safety_signature_back.app.user.dto.UserResponseMessageDTO;
import com.safety_signature.safety_signature_back.app.user.service.UserMasterService;
import com.safety_signature.safety_signature_back.config.FieldSelector;
import com.safety_signature.safety_signature_back.config.Partial;
import com.safety_signature.safety_signature_back.utils.jwt.JwtTokenProvider;
import com.safety_signature.safety_signature_back.utils.jwt.TokenValues;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name="회원 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserMasterResource {
    private final Logger log = LoggerFactory.getLogger(UserMasterResource.class);
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthTransactionalService authTransactionalService;
    private final TokenValues tokenValues;
    private final UserMasterService userMasterService;
    private static final String ENTITY_NAME = "UserMasterResource";
    @Operation(summary = "로그인 요청")
    @GetMapping("/profile")
    public ResponseEntity<?> getUserMe(HttpServletRequest request)throws Exception{
        log.info("get user profile start");
        /**
         * 0. 헤더에 토큰 정보가 있는지 먼저 확인 및 토큰 추출
         * 1. 해더에 담긴 엑세스 토큰이 유효한지 체크한다.
         * 2. 유효하지 않다면 401 에러 리턴
         * 3. 유효한 정보라면 해당 엑세스토큰 정보로 유저정보 리턴
         * */
        UserMasterDTO result = userMasterService.isValidTokenCheckToGetUserMaster(request,tokenValues.secretKey());
        if(ObjectUtils.isEmpty(result)){
            UserResponseMessageDTO messageDTO = new UserResponseMessageDTO();
            messageDTO.setMassage("Status requiring login or sign");

            messageDTO.setStatus(HttpStatus.UNAUTHORIZED);
            return ResponseEntity.status(messageDTO.getStatus()).build();
        }else{
            return ResponseEntity.ok().headers(HttpHeaders.EMPTY).body(Partial.with(
                    result,
                    FieldSelector.withDefaultView(null, View.Min.class)
            ));
        }
    }
}

package com.safety_signature.safety_signature_back.app.auth.resource;

import com.safety_signature.safety_signature_back.app.auth.dto.requestDTO.NormalLoginReqDTO;
import com.safety_signature.safety_signature_back.app.auth.dto.requestDTO.SocialLoginReqDTO;
import com.safety_signature.safety_signature_back.app.auth.dto.responseDTO.LoginResBaseDTO;
import com.safety_signature.safety_signature_back.app.auth.dto.responseDTO.LoginResDTO;
import com.safety_signature.safety_signature_back.app.auth.dto.responseDTO.LoginResMessageDTO;
import com.safety_signature.safety_signature_back.app.auth.service.AuthTransactionalService;
import com.safety_signature.safety_signature_back.app.auth.service.OAuth2Service;
import com.safety_signature.safety_signature_back.app.common.enumeration.SocialTypeCode;
import com.safety_signature.safety_signature_back.app.token.dto.TokenManagementMaterDTO;
import com.safety_signature.safety_signature_back.app.token.service.TokenManagementMasterService;
import com.safety_signature.safety_signature_back.app.user.dto.UserMasterDTO;
import com.safety_signature.safety_signature_back.app.user.service.UserMasterService;
import com.safety_signature.safety_signature_back.utils.PasswordUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name="로그인 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class AuthResource {
    private final Logger log = LoggerFactory.getLogger(AuthResource.class);
    private final OAuth2Service oAuth2Service;
    private final AuthTransactionalService authTransactionalService;
    private final TokenManagementMasterService tokenManagementMasterService;
    private final UserMasterService userMasterService;
    @Operation(summary = "소셜 로그인")
    @PostMapping("/social")
    public ResponseEntity<LoginResBaseDTO> oauthLogin(HttpServletRequest request , @RequestBody @Valid SocialLoginReqDTO socialLoginReqDTO) throws Exception {
            log.info("oauth login start");
            log.info("socialLoginReqDTO: {}", socialLoginReqDTO);
            /**
             * 1. 헤더에 토큰정보가 담겨있는지 확인
             * 2. 소셜로그인 진행
             * 3. 추가 또는 업데이트된 유저정보를 기반으로 엑세스 토큰 , 리프레시 토큰 발행
             * 4. 유저정보와 엑세스토큰, 리프레시토큰 전달
             * */
            /**TODO 추가 이슈사항 .... 네이버로그인은 플러터에서 정식지원을 하지않음. 별도의 로그인 프로세스 필요할듯 */
            try {
                if(SocialTypeCode.NAVER.getValue().equals(SocialTypeCode.from(socialLoginReqDTO.getSocialType()))) {
                    return ResponseEntity.ok().body(oAuth2Service.oauthLogin( null, socialLoginReqDTO));
                }else{
                    log.info("oauth login google or kakao");
                    String accessToken = authTransactionalService.extractTokenFromRequest(request);
                    return ResponseEntity.ok().body(oAuth2Service.oauthLogin(accessToken, socialLoginReqDTO));
                }
            }catch (Exception e) {
                log.info("oauth login Exception : {}", e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }


    }
    @Operation(summary = "일반 로그인")
    @PostMapping("/normal")
    public ResponseEntity<LoginResBaseDTO> normalLogin(@RequestBody @Valid NormalLoginReqDTO normalLoginReqDTO) throws Exception {
        log.info("normalLogin  start");
        log.info("normalLoginReqDTO: {}", normalLoginReqDTO);
        /**
         * 1. userId 기준 회원 정보 조회.
         * 2. 조회된 회원정보에 암호화 돼있는 비밀번호 전달된 비밀번호 비교
         * 3. 검증 완료시 로그인 처리 (엑세스, 리프레시 토큰 발행 후 리턴)
         */
        UserMasterDTO userMasterDTO = userMasterService.getUserMasterByEmail(normalLoginReqDTO.getUserId());
        if(userMasterDTO == null) {
            log.info("userMasterDTO is null");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(LoginResMessageDTO.builder().message("UserID that does not exist").httpStatus(HttpStatus.NO_CONTENT).build());
        }
        if(PasswordUtils.matches(normalLoginReqDTO.getPassword(),userMasterDTO.getUserPassword())){
            TokenManagementMaterDTO tokenManagementMaterDTO = tokenManagementMasterService.createOrUpdateTokenManagementMaster(userMasterDTO);
            return ResponseEntity.ok().body(LoginResDTO.builder().accessToken(tokenManagementMaterDTO.getAccessToken()).refreshToken(tokenManagementMaterDTO.getRefreshToken()).build());
        }else{
            log.info("userMasterDTO password does not match");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(LoginResMessageDTO.builder().message("password does not match").httpStatus(HttpStatus.NO_CONTENT).build());
        }
    }
}

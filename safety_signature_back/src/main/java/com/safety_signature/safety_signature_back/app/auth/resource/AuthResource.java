package com.safety_signature.safety_signature_back.app.auth.resource;

import com.safety_signature.safety_signature_back.app.auth.dto.requestDTO.LoginReqDTO;
import com.safety_signature.safety_signature_back.app.auth.dto.responseDTO.LoginResDTO;
import com.safety_signature.safety_signature_back.app.auth.service.AuthTransactionalService;
import com.safety_signature.safety_signature_back.app.auth.service.OAuth2Service;
import com.safety_signature.safety_signature_back.app.common.enumeration.SocialTypeCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    @Operation(summary = "로그인 요청")
    @PostMapping("/social")
    public ResponseEntity<LoginResDTO> oauthLogin(HttpServletRequest request , @RequestBody @Valid LoginReqDTO loginReqDTO) throws Exception {
            log.info("oauth login start");
            log.info("loginReqDTO: {}", loginReqDTO);
            /**
             * 1. 헤더에 토큰정보가 담겨있는지 확인
             * 2. 소셜로그인 진행
             * 3. 추가 또는 업데이트된 유저정보를 기반으로 엑세스 토큰 , 리프레시 토큰 발행
             * 4. 유저정보와 엑세스토큰, 리프레시토큰 전달
             * */
            /**TODO 추가 이슈사항 .... 네이버로그인은 플러터에서 정식지원을 하지않음. 별도의 로그인 프로세스 필요할듯 */
            if(SocialTypeCode.NAVER.getValue().equals(SocialTypeCode.from(loginReqDTO.getSocialType()))) {
                return ResponseEntity.ok().body(oAuth2Service.oauthLogin( null, loginReqDTO));
            }else{
                String accessToken = authTransactionalService.extractTokenFromRequest(request);
                return ResponseEntity.ok().body(oAuth2Service.oauthLogin(accessToken, loginReqDTO));
            }

    }
}

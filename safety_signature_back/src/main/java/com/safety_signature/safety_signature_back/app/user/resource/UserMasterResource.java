package com.safety_signature.safety_signature_back.app.user.resource;

import com.safety_signature.safety_signature_back.app.auth.dto.responseDTO.LoginResDTO;
import com.safety_signature.safety_signature_back.app.auth.service.AuthTransactionalService;
import com.safety_signature.safety_signature_back.app.common.dto.AttachDocMasterDTO;
import com.safety_signature.safety_signature_back.app.common.dto.View;
import com.safety_signature.safety_signature_back.app.common.enumeration.UserStatusCode;
import com.safety_signature.safety_signature_back.app.common.service.AttachDocMasterService;
import com.safety_signature.safety_signature_back.app.token.dto.TokenManagementMaterDTO;
import com.safety_signature.safety_signature_back.app.token.service.TokenManagementMasterService;
import com.safety_signature.safety_signature_back.app.user.domain.UserMaster;
import com.safety_signature.safety_signature_back.app.user.dto.UserMasterDTO;
import com.safety_signature.safety_signature_back.app.user.dto.UserResponseMessageDTO;
import com.safety_signature.safety_signature_back.app.user.dto.request.PostJoinBody;
import com.safety_signature.safety_signature_back.app.user.dto.response.UserJoinFailedResponseDTO;
import com.safety_signature.safety_signature_back.app.user.repository.UserMasterRepository;
import com.safety_signature.safety_signature_back.app.user.service.UserMasterService;
import com.safety_signature.safety_signature_back.config.FieldSelector;
import com.safety_signature.safety_signature_back.config.Partial;
import com.safety_signature.safety_signature_back.utils.PasswordUtils;
import com.safety_signature.safety_signature_back.utils.common.TsidUtil;
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
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
    private final AttachDocMasterService attachDocMasterService;
    private final TokenManagementMasterService tokenManagementMasterService;
    private static final String ENTITY_NAME = "UserMasterResource";
    private final UserMasterRepository userMasterRepository;

    @Operation(summary = "회원 프로필 요청")
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
            UserResponseMessageDTO messageDTO = UserResponseMessageDTO.builder()
                    .massage("Status requiring login or sign")
                    .Status(HttpStatus.UNAUTHORIZED)
                    .build();
            return ResponseEntity.status(messageDTO.getStatus()).build();
        }else{
            return ResponseEntity.ok().headers(HttpHeaders.EMPTY).body(Partial.with(
                    result,
                    FieldSelector.withDefaultView(null, View.Min.class)
            ));
        }
    }

    @Operation(summary = "회원가입 요청")
    @PostMapping("/join")
    public ResponseEntity<?> postUserJoin(@RequestBody PostJoinBody postJoinBody)throws Exception{
        log.info("post user join start");
        /**
         * 1. id 정보가 있다면 기존 계정 상태 바꿔줘야함 .
         * 2. 비밀번호는 스프링 시큐리티에서 제공하는 암호화 처리
         * 3. 이미지 minio 저장 처리
         * 4. 기존 회원 유무를 체크할건지.. 이건 고민 필요할듯
         */
        String userMasterId =  postJoinBody.getId() ==null ? TsidUtil.getId() : postJoinBody.getId();
        /**
         * 회원가입 실패 조건
         * 1. 이미지 저장실패
         * 2. 이미 가입된 회원일때
         * 3. 이메일, 이름, 전화번호 중복확인 필요함.
         * */
        Optional<UserMaster> existingUserEmail = userMasterRepository.findByEmail(postJoinBody.getUserId());
        Optional<UserMaster> existingUserMobile = userMasterRepository.findByMobile(postJoinBody.getMobile());
        //회원 정보가 존재 한다면 현재 회원 상태코드를 체크. 회원 상태코드가 계정 신청중이 아닐경우
        if(existingUserEmail.isPresent() && !(existingUserEmail.get().getUserStatusCode().equals(UserStatusCode.PENDING))){
            return ResponseEntity.ok().body( UserJoinFailedResponseDTO.builder().message("이미 가입된 이메일이 존재합니다.").userStatusCode(existingUserEmail.get().getUserStatusCode()).build());
        }
        if(existingUserMobile.isPresent() && !(existingUserMobile.get().getUserStatusCode().equals(UserStatusCode.PENDING))){
            return ResponseEntity.ok().body( UserJoinFailedResponseDTO.builder().message("이미 가입된 핸드폰번호가 존재합니다.").userStatusCode(existingUserMobile.get().getUserStatusCode()).build());
        }
        AttachDocMasterDTO attachDocMasterDTO = attachDocMasterService.base64StringSignatureImageSave(postJoinBody.getImage(),userMasterId);

        if(ObjectUtils.isEmpty(attachDocMasterDTO)){
            log.info("post user join failed");
            UserResponseMessageDTO messageDTO = UserResponseMessageDTO.builder()
                    .Status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .massage("전자서명 정보 저장실패")
                    .build();
            return ResponseEntity.status(messageDTO.getStatus()).body(messageDTO);
        }else{
            log.info("post user join success");
            UserMasterDTO userMasterDTO = new UserMasterDTO().builder()
                    .userPassword(PasswordUtils.encodePassword(postJoinBody.getPassword()))
                    .id(userMasterId)
                    .email(postJoinBody.getUserId())
                    .name(postJoinBody.getName())
                    .mobile(postJoinBody.getMobile())
                    .signatureDocId(attachDocMasterDTO.getId())
                    .userStatusCode(UserStatusCode.ACTIVE)
                    .build();
            UserMasterDTO result =  userMasterService.partialUpdate(userMasterDTO);
            TokenManagementMaterDTO tokenManagementMaterDTO= tokenManagementMasterService.createOrUpdateTokenManagementMaster(result);
            return ResponseEntity.ok().body(LoginResDTO.builder().accessToken(tokenManagementMaterDTO.getAccessToken()).refreshToken(tokenManagementMaterDTO.getRefreshToken()).build());
        }


    }
}

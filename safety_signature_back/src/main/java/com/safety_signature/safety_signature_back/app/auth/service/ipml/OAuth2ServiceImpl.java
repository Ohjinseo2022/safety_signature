package com.safety_signature.safety_signature_back.app.auth.service.ipml;


import com.safety_signature.safety_signature_back.app.auth.dto.OauthUserProfileResponse;
import com.safety_signature.safety_signature_back.app.auth.dto.kakaoDTO.KakaoAccount;
import com.safety_signature.safety_signature_back.app.auth.dto.kakaoDTO.KakaoOauthDTO;
import com.safety_signature.safety_signature_back.app.auth.dto.requestDTO.LoginReqDTO;
import com.safety_signature.safety_signature_back.app.auth.dto.responseDTO.LoginResDTO;
import com.safety_signature.safety_signature_back.app.auth.exception.OauthServerException;
import com.safety_signature.safety_signature_back.app.auth.oauth2.OAuthWebClient;
import com.safety_signature.safety_signature_back.app.auth.service.OAuth2Service;
import com.safety_signature.safety_signature_back.app.common.enumeration.SocialTypeCode;
import com.safety_signature.safety_signature_back.app.token.dto.TokenManagementMaterDTO;
import com.safety_signature.safety_signature_back.app.token.service.TokenManagementMasterService;
import com.safety_signature.safety_signature_back.app.user.dto.UserMasterDTO;
import com.safety_signature.safety_signature_back.app.user.service.UserMasterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class OAuth2ServiceImpl implements OAuth2Service {
    private final Logger log = LoggerFactory.getLogger(OAuth2ServiceImpl.class);
    private final OAuthWebClient oAuthWebClient;
    private final UserMasterService userMasterService;
    private final TokenManagementMasterService tokenManagementMasterService;
    public OAuth2ServiceImpl(OAuthWebClient oAuthWebClient, UserMasterService userMasterService, TokenManagementMasterService tokenManagementMasterService) {
        this.oAuthWebClient = oAuthWebClient;
        this.userMasterService = userMasterService;
        this.tokenManagementMasterService = tokenManagementMasterService;
    }

    @Override
    public LoginResDTO oauthLogin(String accessToken, LoginReqDTO loginReqDTO) {
        // 전달 받은 엑세스 토큰을 사용해서 사용자 프로필 정보를 요청해서 가져오기
        String socialTypeCode = SocialTypeCode.from(loginReqDTO.getSocialType());

        if (SocialTypeCode.GOOGLE.getValue().equals(socialTypeCode)){
            OauthUserProfileResponse profileResponse = oAuthWebClient.requestGoogleOauthUserProfile(accessToken);
            if(!ObjectUtils.isEmpty(profileResponse)) {
                UserMasterDTO userMasterDTO =  userMasterService.createOrPartialUpdateUserMaster(profileResponse ,loginReqDTO);
                TokenManagementMaterDTO tokenManagementMaterDTO = tokenManagementMasterService.createOrUpdateTokenManagementMaster(userMasterDTO);
                log.info("userMasterDTO : {}", userMasterDTO);
                return new LoginResDTO(tokenManagementMaterDTO.getAccessToken(),tokenManagementMaterDTO.getRefreshToken());
            }
        }
        if (SocialTypeCode.KAKAO.getValue().equals(socialTypeCode)){
            KakaoOauthDTO profileResponse= oAuthWebClient.requestKakaoOauthUserProfile(accessToken);
            if(!ObjectUtils.isEmpty(profileResponse)) {
                KakaoAccount kakaoAccount = profileResponse.getKakao_account();
                UserMasterDTO userMasterDTO =  userMasterService.createOrPartialUpdateUserMaster(new OauthUserProfileResponse(kakaoAccount.getName(),
                        kakaoAccount.getEmail(),
                        kakaoAccount.getPhone_number(),
                        kakaoAccount.getProfile().getProfile_image_url()) ,
                        loginReqDTO);
                TokenManagementMaterDTO tokenManagementMaterDTO = tokenManagementMasterService.createOrUpdateTokenManagementMaster(userMasterDTO);
                log.info("userMasterDTO : {}", userMasterDTO);
                return new LoginResDTO(tokenManagementMaterDTO.getAccessToken(),tokenManagementMaterDTO.getRefreshToken());
            }
        }
//        if (SocialTypeCode.NAVER.getValue().equals(socialTypeCode)){
//            UserMasterDTO userMasterDTO =  userMasterService.createOrPartialUpdateUserMaster(new OauthUserProfileResponse(loginReqDTO.getName(), loginReqDTO.getEmail(), null) ,loginReqDTO);
//            TokenManagementMaterDTO tokenManagementMaterDTO = tokenManagementMasterService.createOrUpdateTokenManagementMaster(userMasterDTO);
//            return new LoginResDTO(tokenManagementMaterDTO.getAccessToken(),tokenManagementMaterDTO.getRefreshToken());
//        }


        /* TODO: 데이터 베이스에 있는 유저정보 확인. 유저 정보가 존재하면 그대로 엑세스 토큰과 리프레시 토큰 발행
                유저 정보가 없다면 가입 프로세스 진행
        */
        // TODO: 엑세스 토큰및 리프레시 토큰 발행 처리 및 유저 정보 client 로 전송


        throw new OauthServerException("");
    }
}

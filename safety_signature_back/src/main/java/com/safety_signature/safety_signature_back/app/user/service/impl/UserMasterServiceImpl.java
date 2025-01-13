package com.safety_signature.safety_signature_back.app.user.service.impl;
import com.safety_signature.safety_signature_back.app.auth.dto.OauthUserProfileResponse;
import com.safety_signature.safety_signature_back.app.auth.dto.requestDTO.LoginReqDTO;
import com.safety_signature.safety_signature_back.app.auth.service.AuthTransactionalService;
import com.safety_signature.safety_signature_back.app.common.enumeration.SocialTypeCode;
import com.safety_signature.safety_signature_back.app.common.enumeration.UserStatusCode;
import com.safety_signature.safety_signature_back.app.user.domain.UserMaster;
import com.safety_signature.safety_signature_back.app.user.dto.UserMasterDTO;
import com.safety_signature.safety_signature_back.app.user.mapper.UserMaterMapper;
import com.safety_signature.safety_signature_back.app.user.repository.UserMasterRepository;
import com.safety_signature.safety_signature_back.app.user.resource.UserMasterResource;
import com.safety_signature.safety_signature_back.app.user.service.UserMasterService;
import com.safety_signature.safety_signature_back.utils.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserMasterServiceImpl implements UserMasterService {
    private final Logger log = LoggerFactory.getLogger(UserMasterServiceImpl.class);
    private final UserMasterRepository userMasterRepository;
    private final UserMaterMapper userMaterMapper;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthTransactionalService authTransactionalService;
    public UserMasterServiceImpl(UserMasterRepository userMasterRepository, UserMaterMapper userMaterMapper, JwtTokenProvider jwtTokenProvider, AuthTransactionalService authTransactionalService) {
        this.userMasterRepository = userMasterRepository;
        this.userMaterMapper = userMaterMapper;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authTransactionalService = authTransactionalService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserMasterDTO createOrPartialUpdateUserMaster(OauthUserProfileResponse profileResponse , LoginReqDTO loginReqDTO) {
        Optional<UserMaster> existingUserMaster =profileResponse.phoneNumber() == null ? userMasterRepository.findByEmail(profileResponse.email()) :userMasterRepository.findByMobile(profileResponse.phoneNumber()); ;
    /**
     * TODO 해결 해야할 문제점.... 소셜로그인 진행시 구글계정, 카카오계정, 네이버 계정 이메일 정보가 다를 수 있음....
     * */
        UserMasterDTO newUserDTO = new UserMasterDTO();
        String socialTypeCode = SocialTypeCode.from(loginReqDTO.getSocialType());
        newUserDTO.setEmail(profileResponse.email());
        newUserDTO.setName(profileResponse.name());
        if(profileResponse.phoneNumber()!=null){
            newUserDTO.setMobile(profileResponse.phoneNumber());
        }
        newUserDTO.setProfileImageUri(profileResponse.profileImageUri());
        if(!socialTypeCode.isEmpty()){
            if (SocialTypeCode.GOOGLE.getValue().equals(socialTypeCode))   newUserDTO.setGoogleSignIn(true);
            if (SocialTypeCode.KAKAO.getValue().equals(socialTypeCode))    newUserDTO.setKakaoSignIn(true);
            if (SocialTypeCode.NAVER.getValue().equals(socialTypeCode))    newUserDTO.setNaverSignIn(true);
        };
        if (existingUserMaster.isPresent()) {
            return existingUserMaster.map((existingUser) -> {
                newUserDTO.setId(existingUser.getId());
                userMaterMapper.partialUpdate(existingUser, newUserDTO);
                return existingUser;
            }).map(userMasterRepository::save).map(userMaterMapper::toDto).get();
        }else{
            newUserDTO.setUserStatusCode(UserStatusCode.PENDING);
            return userMaterMapper.toDto(userMasterRepository.save(userMaterMapper.toEntity(newUserDTO)));
        }
    }
    public UserMasterDTO partialUpdate(UserMasterDTO userMasterDTO) {
        Optional<UserMaster> existingUserMaster =userMasterRepository.findById(userMasterDTO.getId());
        if (existingUserMaster.isPresent()) {
            return existingUserMaster.map((existingUser) -> {
//                userMasterDTO.setId(existingUser.getId());
                userMaterMapper.partialUpdate(existingUser, userMasterDTO);
                return existingUser;
            }).map(userMasterRepository::save).map(userMaterMapper::toDto).get();
        }else{
            return userMaterMapper.toDto(userMasterRepository.save(userMaterMapper.toEntity(userMasterDTO)));
        }
    }

    @Override
    public UserMasterDTO getUserMasterById(String userId) {
        UserMaster userMaster = userMasterRepository.findById(userId).orElse(null);
        if(userMaster != null){
            return userMaterMapper.toDto(userMaster);
        }
        return null;
    }

    @Override
    public UserMasterDTO isValidTokenCheckToGetUserMaster(HttpServletRequest request, String secretKey) {
        String accessToken = authTransactionalService.extractTokenFromRequest(request);
        Boolean isValidate = jwtTokenProvider.validateToken(accessToken);
        if(isValidate){
            String userId = jwtTokenProvider.getSubject(accessToken, secretKey);
            UserMasterDTO userMasterDTO = this.getUserMasterById(userId);
            return userMasterDTO;
        }
        return null;
    }
}

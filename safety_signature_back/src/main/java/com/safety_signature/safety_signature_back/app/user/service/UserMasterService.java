package com.safety_signature.safety_signature_back.app.user.service;

import com.safety_signature.safety_signature_back.app.auth.dto.OauthUserProfileResponse;
import com.safety_signature.safety_signature_back.app.auth.dto.requestDTO.LoginReqDTO;
import com.safety_signature.safety_signature_back.app.user.dto.UserMasterDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface UserMasterService {


    //TODO: 기존 유저 정보가 있다면 업데이트 기존 유저 정보가 없다면 신규 생성
    UserMasterDTO createOrPartialUpdateUserMaster(OauthUserProfileResponse profileResponse , LoginReqDTO loginReqDTO);
    UserMasterDTO getUserMasterById(String userId);
    UserMasterDTO isValidTokenCheckToGetUserMaster(HttpServletRequest request ,String secretKey);
}

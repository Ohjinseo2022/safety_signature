package com.safety_signature.safety_signature_back.app.auth.service;


import com.safety_signature.safety_signature_back.app.auth.dto.requestDTO.LoginReqDTO;
import com.safety_signature.safety_signature_back.app.auth.dto.responseDTO.LoginResDTO;


public interface OAuth2Service {

    LoginResDTO oauthLogin(String accessToken, LoginReqDTO loginReqDTO);

}

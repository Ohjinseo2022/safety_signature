package com.safety_signature.safety_signature_back.app.token.service;

import com.safety_signature.safety_signature_back.app.token.dto.TokenManagementMaterDTO;
import com.safety_signature.safety_signature_back.app.token.dto.response.ResponseTokenManagementBaseDTO;
import com.safety_signature.safety_signature_back.app.user.dto.UserMasterDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface TokenManagementMasterService {
    TokenManagementMaterDTO createOrUpdateTokenManagementMaster(UserMasterDTO userMasterDTO);
    Boolean existAccessToken(String token);
    Boolean existRefreshToken(String refreshToken);
    Boolean isValidAccessToken(String token);
    Boolean isValidRefreshToken(String token);
    ResponseTokenManagementBaseDTO renewalUpdateToken(HttpServletRequest request);
}

package com.safety_signature.safety_signature_back.app.auth.service;

import com.safety_signature.safety_signature_back.app.token.dto.TokenManagementMaterDTO;
import com.safety_signature.safety_signature_back.app.user.dto.UserMasterDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthTransactionalService {

    String extractTokenFromRequest(HttpServletRequest request);

    TokenManagementMaterDTO createAccessTokenAndRefreshToken(UserMasterDTO userMasterDTO);
}

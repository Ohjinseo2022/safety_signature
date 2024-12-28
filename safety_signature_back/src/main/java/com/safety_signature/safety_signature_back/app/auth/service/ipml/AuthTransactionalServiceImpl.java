package com.safety_signature.safety_signature_back.app.auth.service.ipml;


import com.safety_signature.safety_signature_back.app.auth.service.AuthTransactionalService;
import com.safety_signature.safety_signature_back.app.token.dto.TokenManagementMaterDTO;
import com.safety_signature.safety_signature_back.app.user.dto.UserMasterDTO;
import com.safety_signature.safety_signature_back.utils.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;


@Service
public class AuthTransactionalServiceImpl implements AuthTransactionalService {
    private final JwtTokenProvider jwtTokenProvider;

    public AuthTransactionalServiceImpl( JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }


    @Override
    public String extractTokenFromRequest(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        validationAuthorizationHeader(authorizationHeader);
        return extractToken(authorizationHeader);
    }
    private void validationAuthorizationHeader(String header) {
        if (header == null || !header.startsWith("Bearer ")) {
            throw new IllegalArgumentException();
        }
    }
    private String extractToken(String authorizationHeader) {
        return authorizationHeader.substring("Bearer ".length());
    }
    @Override
    public TokenManagementMaterDTO createAccessTokenAndRefreshToken(UserMasterDTO userMasterDTO) {
        String accessToken = jwtTokenProvider.createAccessToken(userMasterDTO.getId());
        String refreshToken = jwtTokenProvider.createRefreshToken(userMasterDTO.getId());
        return new TokenManagementMaterDTO(null,userMasterDTO.getId(),null,accessToken, refreshToken);
    }
}

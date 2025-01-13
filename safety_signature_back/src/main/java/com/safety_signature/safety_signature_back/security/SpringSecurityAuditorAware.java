package com.safety_signature.safety_signature_back.security;


import com.safety_signature.safety_signature_back.app.user.dto.UserMasterDTO;
import com.safety_signature.safety_signature_back.app.user.service.UserMasterService;
import com.safety_signature.safety_signature_back.utils.jwt.TokenValues;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.AuditorAware;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {
    final UserMasterService userMasterService;
    private final TokenValues tokenValues;

    public SpringSecurityAuditorAware(UserMasterService userMasterService, TokenValues tokenValues) {
        this.userMasterService = userMasterService;
        this.tokenValues = tokenValues;
    }

    @Override
    public Optional<String> getCurrentAuditor() {
        //createdBy lastModifiedBy 를 자동으로 입력시켜주는 코드
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                return  Optional.of("anonymoususer");
            }
            UserMasterDTO userMasterDTO = userMasterService.isValidTokenCheckToGetUserMaster(request, tokenValues.secretKey());
            if (userMasterDTO == null) {
                Optional.of("anonymoususer");
            }
            return Optional.of(userMasterDTO.getName());
        } catch (JwtException jwtException) {
           return  Optional.of("anonymoususer");
        }
    }
}

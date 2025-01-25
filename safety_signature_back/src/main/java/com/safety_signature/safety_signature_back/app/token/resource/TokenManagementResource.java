package com.safety_signature.safety_signature_back.app.token.resource;

import com.safety_signature.safety_signature_back.app.token.dto.response.RenewalTokenErrorMessageDTO;
import com.safety_signature.safety_signature_back.app.token.dto.response.RenewalTokenSuccessDTO;
import com.safety_signature.safety_signature_back.app.token.dto.response.ResponseTokenManagementBaseDTO;
import com.safety_signature.safety_signature_back.app.token.service.TokenManagementMasterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name="토큰 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/token")
public class TokenManagementResource {
    private final TokenManagementMasterService tokenManagementMasterService;

    @Operation(summary = "토큰 갱신요청")
    @PostMapping("/renew")
    public ResponseEntity<ResponseTokenManagementBaseDTO> postTokenRefresh(HttpServletRequest request) {
        ResponseTokenManagementBaseDTO result = tokenManagementMasterService.renewalUpdateToken(request);
        if(result instanceof RenewalTokenSuccessDTO){
            return ResponseEntity.ok().body(result);
        }
        return ResponseEntity.status(((RenewalTokenErrorMessageDTO) result).getErrorCode()).body(result);
    }
}

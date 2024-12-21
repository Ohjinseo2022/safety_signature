package com.safety_signature.safety_signature_back.app.token.service.impl;

import com.safety_signature.safety_signature_back.app.auth.service.AuthTransactionalService;
import com.safety_signature.safety_signature_back.app.token.domain.TokenManagementMaster;
import com.safety_signature.safety_signature_back.app.token.dto.TokenManagementMaterDTO;
import com.safety_signature.safety_signature_back.app.token.dto.response.RenewalTokenErrorMessageDTO;
import com.safety_signature.safety_signature_back.app.token.dto.response.RenewalTokenSuccessDTO;
import com.safety_signature.safety_signature_back.app.token.dto.response.ResponseTokenManagementBaseDTO;
import com.safety_signature.safety_signature_back.app.token.mapper.TokenManagementMapper;
import com.safety_signature.safety_signature_back.app.token.repository.TokenManagementRepository;
import com.safety_signature.safety_signature_back.app.token.service.TokenManagementMasterService;
import com.safety_signature.safety_signature_back.app.user.dto.UserMasterDTO;
import com.safety_signature.safety_signature_back.app.user.service.UserMasterService;
import com.safety_signature.safety_signature_back.utils.jwt.JwtTokenProvider;
import com.safety_signature.safety_signature_back.utils.jwt.TokenValues;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

@Service
public class TokenManagementMasterServiceImpl implements TokenManagementMasterService {
    private final AuthTransactionalService authTransactionalService;
    private final TokenManagementRepository tokenManagementRepository;
    private final TokenManagementMapper tokenManagementMapper;
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenValues tokenValues;
    private final UserMasterService userMasterService;

    public TokenManagementMasterServiceImpl(AuthTransactionalService authTransactionalService, TokenManagementRepository tokenManagementRepository, TokenManagementMapper tokenManagementMapper, JwtTokenProvider jwtTokenProvider, TokenValues tokenValues, UserMasterService userMasterService) {
        this.authTransactionalService = authTransactionalService;
        this.tokenManagementRepository = tokenManagementRepository;
        this.tokenManagementMapper = tokenManagementMapper;
        this.jwtTokenProvider = jwtTokenProvider;
        this.tokenValues = tokenValues;
        this.userMasterService = userMasterService;
    }

    @Override
    public TokenManagementMaterDTO createOrUpdateTokenManagementMaster(UserMasterDTO userMasterDTO) {
        /**
         * 1. 기존 토큰 관리 테이블에서 유저마스터 정보로 조회
         * 2. 기존 토큰 관리 테이블에 정보가 존재하면 업데이트 존재하지 않으면 신규 생성.
         * */
        Optional<TokenManagementMaster> tokenManagementMaster =  tokenManagementRepository.findByUserMaster_Id(userMasterDTO.getId());
        TokenManagementMaterDTO newTokenDTO = authTransactionalService.createAccessTokenAndRefreshToken(userMasterDTO);
        if(tokenManagementMaster.isPresent()) {
            return tokenManagementMaster.map((existingToken)->{
                newTokenDTO.setId(existingToken.getId());
                tokenManagementMapper.partialUpdate(existingToken,newTokenDTO);
                return existingToken;
            }).map(tokenManagementRepository::save).map(tokenManagementMapper::toDto).get();
        }else{
            return tokenManagementMapper.toDto(tokenManagementRepository.save(tokenManagementMapper.toEntity(newTokenDTO)));
        }
    }

    @Override
    public Boolean existAccessToken(String token) {
        TokenManagementMaster tokenManagementMaster = tokenManagementRepository.findByAccessToken(token);
        if(ObjectUtils.isEmpty(tokenManagementMaster)) return false;
        if(ObjectUtils.isEmpty(tokenManagementMaster.getAccessToken())) return false;
        return true;
    }
    @Override
    public Boolean existRefreshToken(String token) {
        TokenManagementMaster tokenManagementMaster = tokenManagementRepository.findByRefreshToken(token);
        if(ObjectUtils.isEmpty(tokenManagementMaster)) return false;
        if(ObjectUtils.isEmpty(tokenManagementMaster.getRefreshToken())) return false;
        return true;
    }

    @Override
    public Boolean isValidAccessToken(String token) {
        Boolean existAccessToken = this.existAccessToken(token);
        Boolean isValidate = jwtTokenProvider.validateToken(token);
        return existAccessToken && isValidate;
    }
    @Override
    public Boolean isValidRefreshToken(String token) {
        Boolean existAccessToken = this.existRefreshToken(token);
        Boolean isValidate = jwtTokenProvider.validateToken(token);
        return existAccessToken && isValidate;
    }

    @Override
    public ResponseTokenManagementBaseDTO renewalUpdateToken(HttpServletRequest request) {
        String token = authTransactionalService.extractTokenFromRequest(request);
        Boolean isValid = this.isValidRefreshToken(token);

        if(isValid){
            try{
                String userId = jwtTokenProvider.getSubject(token, tokenValues.secretKey());
                UserMasterDTO userMasterDTO = userMasterService.getUserMasterById(userId);
                TokenManagementMaterDTO tokenManagementMaterDTO = this.createOrUpdateTokenManagementMaster(userMasterDTO);
                return new RenewalTokenSuccessDTO(tokenManagementMaterDTO.getAccessToken() , tokenManagementMaterDTO.getRefreshToken());
           }catch (Exception e){
                return new RenewalTokenErrorMessageDTO(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }
        return new RenewalTokenErrorMessageDTO("리프레시토큰 권한 만료", HttpStatus.UNAUTHORIZED);
    }

}

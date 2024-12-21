package com.safety_signature.safety_signature_back.app.token.repository;

import com.safety_signature.safety_signature_back.app.token.domain.TokenManagementMaster;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@SuppressWarnings("unused")
@Repository
public interface TokenManagementRepository extends CrudRepository<TokenManagementMaster,String>, JpaSpecificationExecutor<TokenManagementMaster> {
    TokenManagementMaster findByAccessToken(String accessToken);
    TokenManagementMaster findByRefreshToken(String refreshToken);
    Optional<TokenManagementMaster> findByUserMaster_Id(String userMasterId);
}

package com.safety_signature.safety_signature_back.utils.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public record TokenValues(
        @Value("${access-token.secret-key}") String secretKey,
        @Value("${access-token.expire-length}") long accessTokenExpireLength,
        @Value("${refresh-token.expire-length}") long refreshTokenExpireLength
) {}

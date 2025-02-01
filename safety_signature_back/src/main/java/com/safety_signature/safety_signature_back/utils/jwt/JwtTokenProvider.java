package com.safety_signature.safety_signature_back.utils.jwt;


import com.safety_signature.safety_signature_back.app.auth.exception.AccessTokenExpiredException;
import com.safety_signature.safety_signature_back.app.auth.exception.InvalidAccessTokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final TokenValues tokenValues;

    public JwtTokenProvider(TokenValues tokenValues) {
        this.tokenValues = tokenValues;
    }

    public String createAccessToken(String userId) {
        return createToken(userId,tokenValues.accessTokenExpireLength(),tokenValues.secretKey());
    }

    public String createRefreshToken(String userId) {
        return createToken(userId,tokenValues.refreshTokenExpireLength(),tokenValues.secretKey());
    }

    public String createToken(String subject, Long tokenExpireLength, String secretKey) {
        Claims claims = generateClaims(subject, tokenExpireLength);

        return Jwts.builder()
                .setHeaderParam("alg", "HS256")
                .setHeaderParam("typ", "JWT")
                .setClaims(claims)
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)),
                        SignatureAlgorithm.HS256)
                .compact();
    }
    // 토큰 만료 정보 생성
    private Claims generateClaims(String subject, Long tokenExpireLength) {
        Date now = new Date();
        Date expiredAt = new Date(now.getTime() + tokenExpireLength);

        return Jwts.claims()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiredAt);
    }

    //토큰에서 회원 정보 추출
    public String getSubject(String accessToken, String secretKey) {
        try {
            if(tokenValues.secretKey().equals(secretKey)) {
                return Jwts.parserBuilder()
                        .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                        .build()
                        .parseClaimsJws(accessToken)
                        .getBody()
                        .getSubject();
            }else{
                throw new InvalidAccessTokenException();
            }

        } catch (ExpiredJwtException exception) {
            throw new AccessTokenExpiredException();
        } catch (JwtException exception) {
            throw new InvalidAccessTokenException();
        }
    }
    // 토큰 유효성 검증
    public boolean validateToken(String token) {
        try{
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(tokenValues.secretKey().getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        }catch (ExpiredJwtException exception){
            return false;
        }
    }
    // 요청 헤더에서 토큰 추출
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}

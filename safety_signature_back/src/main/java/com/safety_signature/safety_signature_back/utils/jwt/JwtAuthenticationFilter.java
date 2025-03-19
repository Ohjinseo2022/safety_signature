package com.safety_signature.safety_signature_back.utils.jwt;

import com.safety_signature.safety_signature_back.app.user.dto.UserMasterDTO;
import com.safety_signature.safety_signature_back.app.user.service.UserMasterService;
import com.safety_signature.safety_signature_back.utils.AuthorityUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final TokenValues tokenValues;
    private final JwtTokenProvider jwtTokenProvider; // 토큰 유효성 검증을 위한 Provider
   private final UserMasterService userMasterService;// 사용자 정보 조회
    // ✅ JWT 필터를 적용하지 않을 URL 목록
    private final List<String> excludeUrls = List.of(
            "/services/backend/attach/download","/services/backend/login/social","/services/backend/login/normal", "/services/backend/user/join", "/swagger/**", "/v3/api-docs/**"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestUri = request.getRequestURI();
        // ✅ JWT 검증이 필요 없는 URL이면 필터를 건너뛴다.
        if (excludeUrls.stream().anyMatch(requestUri::startsWith)) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = jwtTokenProvider.resolveToken(request);
        if (token != null && jwtTokenProvider.validateToken(token)) {
            /**
             *  TODO : 다른 플랫폼에서 로그인시 기존 페이지 흠..
             */
            String userId = jwtTokenProvider.getSubject(token,tokenValues.secretKey());
            UserMasterDTO userMasterDTO = userMasterService.getUserMasterById(userId);
            // 기존 인증 정보가 없을 경우만 설정
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userMasterDTO, null, AuthorityUtils.convertToAuthorities(userMasterDTO.getUserTypeCode()));
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // SecurityContext에 인증 정보 저장
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        // 필터 체인 실행
        filterChain.doFilter(request, response);
    }
}

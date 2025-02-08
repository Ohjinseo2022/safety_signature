package com.safety_signature.safety_signature_back.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safety_signature.safety_signature_back.app.user.service.UserMasterService;
import com.safety_signature.safety_signature_back.app.user.service.impl.UserMasterServiceImpl;
import com.safety_signature.safety_signature_back.utils.jwt.JwtAuthenticationFilter;
import com.safety_signature.safety_signature_back.utils.jwt.JwtTokenProvider;
import com.safety_signature.safety_signature_back.utils.jwt.TokenValues;
import jakarta.servlet.DispatcherType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.PrintWriter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SpringSecurityConfig {

    private final UserMasterService userMasterService;

    public SpringSecurityConfig( UserMasterService userMasterService) {
        this.userMasterService = userMasterService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtTokenProvider jwtTokenProvider, UserMasterServiceImpl userMasterServiceImpl, TokenValues tokenValues) throws Exception {
        http
            .cors((corsConfigurer)->corsConfigurer.disable())
//                .headers((httpSecurityHeadersConfigurer ->
//                        httpSecurityHeadersConfigurer.disable()
//                ))
                .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests((authorizeRequests) ->
                    authorizeRequests
                            .requestMatchers("/**").permitAll()// 필요에 따라 허용할 url 설정
                            .requestMatchers("/login/**").permitAll()
                            .requestMatchers("/user/join").permitAll()
                            .requestMatchers("/swagger/**").permitAll()
                            .requestMatchers("/v3/api-docs/**").permitAll()
                            .requestMatchers("/auth/**").permitAll()
                            .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                            .anyRequest().authenticated()// 허용된 url 이 아니면 권한 체크 필요함 !?
//                            .anyRequest().permitAll()// 허용된 url 이 아니면 권한 체크 필요함 !?
            )

            .exceptionHandling((exceptionConfig) ->
                        exceptionConfig.authenticationEntryPoint(unauthorizedEntryPoint).accessDeniedHandler(accessDeniedHandler)
            )// 401 403 관련 예외처리
                .addFilterBefore(new JwtAuthenticationFilter(tokenValues,jwtTokenProvider,userMasterService), UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }
    private final AuthenticationEntryPoint unauthorizedEntryPoint =
            (request, response, authException) -> {
                ErrorResponse fail = new ErrorResponse(HttpStatus.UNAUTHORIZED, "Spring security unauthorized...");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                String json = new ObjectMapper().writeValueAsString(fail);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                PrintWriter writer = response.getWriter();
                writer.write(json);
                writer.flush();
            };

    private final AccessDeniedHandler accessDeniedHandler =
            (request, response, accessDeniedException) -> {
                ErrorResponse fail = new ErrorResponse(HttpStatus.FORBIDDEN, "Spring security forbidden...");
                response.setStatus(HttpStatus.FORBIDDEN.value());
                String json = new ObjectMapper().writeValueAsString(fail);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                PrintWriter writer = response.getWriter();
                writer.write(json);
                writer.flush();
            };

    @Getter
    @RequiredArgsConstructor
    public class ErrorResponse {

        private final HttpStatus status;
        private final String message;
    }
}

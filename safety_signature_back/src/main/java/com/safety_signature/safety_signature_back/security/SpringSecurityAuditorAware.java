package com.safety_signature.safety_signature_back.security;


import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        //createdBy lastModifiedBy 를 자동으로 입력시켜주는 코드
        return Optional.of("system");

        //TODO: 로그인 유저 체크해서 해당 정보가 입력들어갈수 있게 처리해야함
    }
}

package com.safety_signature.safety_signature_back.utils;

import com.safety_signature.safety_signature_back.app.common.enumeration.UserTypeCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;

public class AuthorityUtils {
    // ✅ UserTypeCode를 Spring Security 권한(Collection<GrantedAuthority>)으로 변환
    public static List<GrantedAuthority> convertToAuthorities(UserTypeCode userTypeCode) {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + userTypeCode.name()));
    }
}

package com.safety_signature.safety_signature_back.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtils {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // 비밀번호 해시
    public static String encodePassword(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    // 비밀번호 확인
    public static boolean matches(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }
}

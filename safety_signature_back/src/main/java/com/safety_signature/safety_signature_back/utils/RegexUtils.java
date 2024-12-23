package com.safety_signature.safety_signature_back.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {
    public static String convertPhoneNumber(String phoneNumber) {
        // 정규식 패턴: 국가 코드가 없을 수도 있고, 010-xxxx-xxxx 형식으로 매칭됨
        String regex = "^\\+?\\d{1,3}?[ -]?(10|11|12|13|14|15|16|17|18|19)[ -]?(\\d{4})[ -]?(\\d{4})$";

        // 정규식 패턴을 컴파일하여 패턴 객체 생성
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);

        // 전화번호가 정규식과 매칭될 경우
        if (matcher.matches()) {
            // 국가 코드는 무시하고, 010-xxxx-xxxx 형식으로 변환
            return "010-" + matcher.group(2) + "-" + matcher.group(3);
        }

        // 형식에 맞지 않으면 원래의 번호를 그대로 반환
        return phoneNumber;
    }
}

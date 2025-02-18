package com.safety_signature.safety_signature_back.utils;


import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import java.util.Arrays;
import java.util.Optional;

@Slf4j
public class HttpServletRequestUtil {

    public static String clientIpAddr() {

        // request.getRemoteAddr()
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        String clientIp = request.getHeader("X-Forwarded-For");
        if (clientIp == null) {
            clientIp = request.getHeader("Proxy-Client-IP");
        }
        if (clientIp == null) {
            clientIp = request.getHeader("WL-Proxy-Client-IP");
        }
        if (clientIp == null) {
            clientIp = request.getHeader("HTTP_CLIENT_IP");
        }
        if (clientIp == null) {
            clientIp = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (clientIp == null) {
            clientIp = request.getRemoteAddr();
        }

        log.info("X-Forwarded-For : {}", request.getHeader("X-Forwarded-For"));
        log.info("Proxy-Client-IP : {}", request.getHeader("Proxy-Client-IP"));
        log.info("WL-Proxy-Client-IP : {}", request.getHeader("WL-Proxy-Client-IP"));
        log.info("HTTP_CLIENT_IP : {}", request.getHeader("HTTP_CLIENT_IP"));
        log.info("HTTP_X_FORWARDED_FOR : {}", request.getHeader("HTTP_X_FORWARDED_FOR"));
        log.info("getRemoteAddr : {}", request.getRemoteAddr());

        return Arrays.stream(clientIp.split(",")).findFirst().orElse(clientIp);
    }

    public static String sessionId() {

        // request.getRemoteAddr()
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String sessionId = req.getSession().getId();

        return sessionId;
    }
}

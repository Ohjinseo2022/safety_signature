package com.safety_signature.safety_signature_back.app.common.dto;

public class View {

    public interface Min {}                     // code 성 조회, key/value 정도 조회시
    public interface Summary extends Min {}     // LIST 조회
    public interface Basic extends Summary {}
    public interface Detail extends Basic {}    // 상세 조회
    public interface Hidden extends Detail {}    // 상세 조회
}
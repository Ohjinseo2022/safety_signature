package com.safety_signature.safety_signature_back.app.auth.exception;


import com.safety_signature.safety_signature_back.app.common.exception.CommonException;

public class OauthServerException extends CommonException {

    private static final String MESSAGE = "Oauth 서버 요청에서 예외가 발생했습니다.";

    public OauthServerException(String externalOauthServerMessage) {
        super(MESSAGE + ": " + externalOauthServerMessage);
    }
}

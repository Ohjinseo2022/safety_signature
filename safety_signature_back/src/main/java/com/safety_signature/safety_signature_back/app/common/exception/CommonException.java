package com.safety_signature.safety_signature_back.app.common.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommonException extends RuntimeException {

    protected CommonException(String message) {
        super(message);
    }
}

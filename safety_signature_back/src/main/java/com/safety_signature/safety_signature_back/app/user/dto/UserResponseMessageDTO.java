package com.safety_signature.safety_signature_back.app.user.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class UserResponseMessageDTO {
    private String massage;
    private HttpStatus Status;
}

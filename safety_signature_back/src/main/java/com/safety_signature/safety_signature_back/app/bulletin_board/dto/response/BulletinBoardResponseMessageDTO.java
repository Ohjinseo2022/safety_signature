package com.safety_signature.safety_signature_back.app.bulletin_board.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BulletinBoardResponseMessageDTO extends BulletinBoardResponseBaseDTO{
    HttpStatus httpStatus;
    String message;
}

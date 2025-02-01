package com.safety_signature.safety_signature_back.app.bulletin_board.resource;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name="전자결제 게시판 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/bulletin-board")
public class BulletinBoardResource {

    @Operation(summary = "전자 결제 문서 등록")
    @PutMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody String ss) {
        return null;
    }

}

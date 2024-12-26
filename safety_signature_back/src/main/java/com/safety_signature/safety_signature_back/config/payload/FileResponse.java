package com.safety_signature.safety_signature_back.config.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class FileResponse implements Serializable {
    String filename;
    MediaType contentType;
    BigDecimal fileSize;
    LocalDateTime createdTime;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Schema(hidden = true)
    transient InputStreamResource stream;
}

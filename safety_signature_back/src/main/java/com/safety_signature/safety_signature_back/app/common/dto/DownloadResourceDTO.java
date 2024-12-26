package com.safety_signature.safety_signature_back.app.common.dto;

import lombok.Data;
import org.springframework.core.io.Resource;

@Data
public class DownloadResourceDTO {

    private Resource resource;
    private String fileName;
}

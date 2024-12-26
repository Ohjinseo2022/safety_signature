package com.safety_signature.safety_signature_back.app.common.resource;

import com.safety_signature.safety_signature_back.app.common.dto.AttachDocHistDTO;
import com.safety_signature.safety_signature_back.app.common.dto.AttachDocMasterDTO;
import com.safety_signature.safety_signature_back.app.common.enumeration.AttachDocOwnerClassCode;
import com.safety_signature.safety_signature_back.app.common.enumeration.OperationTypeCode;
import com.safety_signature.safety_signature_back.app.common.service.AttachDocHistService;
import com.safety_signature.safety_signature_back.app.common.service.AttachDocMasterService;
import com.safety_signature.safety_signature_back.app.common.service.impl.AttachDocMasterServiceImpl;
import com.safety_signature.safety_signature_back.app.user.dto.UserMasterDTO;
import com.safety_signature.safety_signature_back.app.user.service.UserMasterService;
import com.safety_signature.safety_signature_back.utils.FileUtil;
import com.safety_signature.safety_signature_back.utils.MinioUtils;
import com.safety_signature.safety_signature_back.utils.jwt.TokenValues;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.utils.FileNameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.regex.Pattern;

@Tag(name = "첨부파일 공통 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/attach")
public class AttachDocMasterResource {
    private final Logger log = LoggerFactory.getLogger(AttachDocMasterResource.class);

    private final TokenValues tokenValues;
    private final UserMasterService userMasterService;
    private final MinioUtils minioUtils;
    private final AttachDocMasterService attachDocMasterService;
    private final AttachDocHistService attachDocHistService;
    @Operation(summary = "이미지 저장 공통")
    @PutMapping(value ="/save-image" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void saveImage(@RequestParam("file") MultipartFile file) throws Exception {
        //HttpServletRequest request
//        UserMasterDTO userMasterDTO = userMasterService.isValidTokenCheckToGetUserMaster(request,tokenValues.secretKey());

        if (file != null) {
            String extension = FileNameUtils.getExtension(file.getOriginalFilename());
            //userMasterDTO.getId()
            String directory = String.format("signature/%s/", "test");
            if (Pattern.matches("(?i)(GIF|JPG|JPEG|PNG|PDF)$", extension)) {
                String storedFileName = minioUtils.upload(file, directory);

                AttachDocMasterDTO attachDocMasterDTO = AttachDocMasterDTO.builder()
                        .attachDocName(file.getOriginalFilename())
                        .attachDocExplain("가격협의내역 첨부파일")
                        .attachDocId("")
                        .attachDocPosition(String.format("%s/%s", directory, storedFileName))
                        .attachDocOwnerClassCode(AttachDocOwnerClassCode.CONTENTS_INQUIRY)
                        .attachDocOwnerId("testID")//userMasterDTO.getId()
                        .attachDocSize(FileUtil.getFileSize(file.getSize()))
                        .build();
                attachDocMasterService.save(attachDocMasterDTO);

                AttachDocHistDTO attachDocHistDTO = AttachDocHistDTO.builder()
                        .attachDocMaster(attachDocMasterDTO)
                        .operationTypeCode(OperationTypeCode.UP_LOADING)
                        .operationGoalExplain(attachDocMasterDTO.getAttachDocExplain())
                        .operatorIpAddress("test중")
                        .build();
                attachDocHistService.save(attachDocHistDTO);
            }
        }
    }

}

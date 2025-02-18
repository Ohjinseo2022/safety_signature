package com.safety_signature.safety_signature_back.app.common.resource;

import com.safety_signature.safety_signature_back.app.common.dto.AttachDocHistDTO;
import com.safety_signature.safety_signature_back.app.common.dto.AttachDocMasterDTO;
import com.safety_signature.safety_signature_back.app.common.dto.DownloadResourceDTO;
import com.safety_signature.safety_signature_back.app.common.enumeration.AttachDocOwnerClassCode;
import com.safety_signature.safety_signature_back.app.common.enumeration.OperationTypeCode;
import com.safety_signature.safety_signature_back.app.common.service.AttachDocHistService;
import com.safety_signature.safety_signature_back.app.common.service.AttachDocMasterService;
import com.safety_signature.safety_signature_back.app.common.service.impl.AttachDocMasterServiceImpl;
import com.safety_signature.safety_signature_back.app.user.dto.UserMasterDTO;
import com.safety_signature.safety_signature_back.app.user.service.UserMasterService;
import com.safety_signature.safety_signature_back.utils.FileUtil;
import com.safety_signature.safety_signature_back.utils.HttpServletRequestUtil;
import com.safety_signature.safety_signature_back.utils.MinioUtils;
import com.safety_signature.safety_signature_back.utils.jwt.TokenValues;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.utils.FileNameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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

    @Operation(summary = "첨부 파일 업로드")
    @PutMapping(value ="/save-signature" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void saveImage(HttpServletRequest request ,
                          @RequestParam(value = "file") MultipartFile file,
                          @RequestParam(value = "attachDocOwnerId",required = false) String attachDocOwnerId) throws Exception {
        //HttpServletRequest request
//        UserMasterDTO userMasterDTO = userMasterService.isValidTokenCheckToGetUserMaster(request,tokenValues.secretKey());
        /**
         * 1. 회원 정보를 조회한다.
         * 2. 히원 정보가 없는 상태에선 진행하지 않는다.
         * 3. 첨부 문서 ID 정보가 있다면 덮어 씌우고 기존 minio 정보를 없앤다.
         * 4. 첨부 문서 ID 정보가 없다면 업로드. 첨부문서 저장.첨부문서 이력 저장. 회원정보에 업데이트 해준다.
         */
        String extension = FileNameUtils.getExtension(file.getOriginalFilename());
        //userMasterDTO.getId()
        String directory = String.format("signature/%s/", "test");
        if (Pattern.matches("(?i)(GIF|JPG|JPEG|PNG|PDF)$", extension)) {
            String storedFileName = minioUtils.upload(file, directory);

            AttachDocMasterDTO attachDocMasterDTO = AttachDocMasterDTO.builder()
                    .attachDocName(file.getOriginalFilename())
                    .attachDocExplain("")
                    .attachDocId("")
                    .attachDocPosition(String.format("%s/%s", directory, storedFileName))
                    .attachDocOwnerClassCode(AttachDocOwnerClassCode.USER_SIGNATURE)
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
    @Operation(summary = "첨부 파일 다운로드")
    @GetMapping(value="/download/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> download(HttpServletRequest request, @PathVariable String id) throws Exception {
        log.debug("REST request to get attachDocMaster : attachDocId {}", id);
        DownloadResourceDTO downloadResourceDTO = attachDocMasterService.downloadAttachDocMasterById(id, HttpServletRequestUtil.clientIpAddr());
        if(!ObjectUtils.isEmpty(downloadResourceDTO)) {
            return ResponseEntity.ok() .header("Content-type", "application/octet-stream")
                    .header(
                            HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\""
                                    + URLEncoder.encode(downloadResourceDTO.getFileName(), StandardCharsets.UTF_8)
                                    + "\""
                    )
                    .body(downloadResourceDTO.getResource());
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}

package com.safety_signature.safety_signature_back.app.approve_master.resource;

import com.safety_signature.safety_signature_back.app.approve_master.domain.ApproveMaster;
import com.safety_signature.safety_signature_back.app.approve_master.dto.ApproveMasterDTO;
import com.safety_signature.safety_signature_back.app.approve_master.dto.request.ApproveCompletedSignatureRequestDTO;
import com.safety_signature.safety_signature_back.app.approve_master.dto.response.ApproveMasterCustomDTO;
import com.safety_signature.safety_signature_back.app.approve_master.dto.response.ApproveMasterListCustomDTO;
import com.safety_signature.safety_signature_back.app.approve_master.dto.response.ApproveMasterListDTO;
import com.safety_signature.safety_signature_back.app.approve_master.dto.response.ApproveMasterMessageDTO;
import com.safety_signature.safety_signature_back.app.approve_master.service.ApproveMasterService;
import com.safety_signature.safety_signature_back.app.bulletin_board.dto.BulletinBoardMasterDTO;
import com.safety_signature.safety_signature_back.app.bulletin_board.dto.response.BulletinBoardResponseMessageDTO;
import com.safety_signature.safety_signature_back.app.bulletin_board.service.BulletinBoardMasterService;
import com.safety_signature.safety_signature_back.app.common.dto.ResponseDTO;
import com.safety_signature.safety_signature_back.app.common.dto.util.InfiniteScrollResponseDTO;
import com.safety_signature.safety_signature_back.app.user.dto.UserMasterDTO;
import com.safety_signature.safety_signature_back.app.user.service.UserMasterService;
import com.safety_signature.safety_signature_back.config.Constants;
import com.safety_signature.safety_signature_back.utils.PaginationUtil;
import com.safety_signature.safety_signature_back.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.*;

import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Tag(name = "전자결제 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/approve")
public class ApproveMasterResource {
    private final Logger log = LoggerFactory.getLogger(ApproveMasterResource.class);
    private final ApproveMasterService approveMasterService;
    private final BulletinBoardMasterService bulletinBoardMasterService;
    private final UserMasterService userMasterService;
    @Value("${api.domain}")
    private String domain;
    @Value("${api.baseUrl}")
    private String baseUrl;
    @Operation(summary = "전자 결제 내역 조회")
    @GetMapping(value = "/completed-list/{bulletinBoardId}")
    public ResponseEntity<ResponseDTO> getCompletedList( @PathVariable String bulletinBoardId, Pageable pageable) {
        String userEmail = SecurityUtils.getCurrentUserLogin().orElse(null);
        // 유저정보가 없다면 401 코드를 전송시켜서, 토큰 갱신 API 호출을 유도함.
        if (Constants.ANONYMOUSUSER.equals(userEmail)|| userEmail ==null) {
            ApproveMasterMessageDTO result = ApproveMasterMessageDTO.builder().httpStatus(HttpStatus.UNAUTHORIZED.value()).message("UNAUTHORIZED").build();
            return ResponseEntity.status(result.getHttpStatus()).body(result);
        }
        BulletinBoardMasterDTO bulletinBoardMasterDTO =  bulletinBoardMasterService.getBulletinBoardMasterDTO(bulletinBoardId);
        if(ObjectUtils.isEmpty(bulletinBoardMasterDTO)){
            ApproveMasterMessageDTO masterMessageDTO = ApproveMasterMessageDTO.builder().message("존재하지 않는 전자 결제 게시글 입니다.").httpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value()).build();
            return ResponseEntity.status(masterMessageDTO.getHttpStatus()).body(masterMessageDTO);
        }
        // =========================================================================================================
        // 페이징 설정
        // =========================================================================================================
        if(ObjectUtils.isEmpty(pageable)) {
            pageable =  PageRequest.of(0, 10, Sort.by(Sort.Order.asc("createdDate")));
        } else {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Order.asc("createdDate")));
        }
        Page<ApproveMasterCustomDTO> result =  approveMasterService.getAllApproveMasters(bulletinBoardMasterDTO, pageable);
        HttpHeaders headers =  PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), result);
        return ResponseEntity.ok().headers(headers).body(ApproveMasterListDTO.builder().data(result.getContent()).build());
    }
    @Operation(summary = "전자 결제 전체 리스트 조회")
    @GetMapping(value = "/completed-table-list/{bulletinBoardId}")
    public ResponseEntity<ResponseDTO> getInfiniteScrollList( @PathVariable String bulletinBoardId,@RequestParam(defaultValue = "20") int size) {
        String userEmail = SecurityUtils.getCurrentUserLogin().orElse(null);
        // 유저정보가 없다면 401 코드를 전송시켜서, 토큰 갱신 API 호출을 유도함.
        if (Constants.ANONYMOUSUSER.equals(userEmail)|| userEmail ==null) {
            ApproveMasterMessageDTO result = ApproveMasterMessageDTO.builder().httpStatus(HttpStatus.UNAUTHORIZED.value()).message("UNAUTHORIZED").build();
            return ResponseEntity.status(result.getHttpStatus()).body(null);
        }
        BulletinBoardMasterDTO bulletinBoardMasterDTO =  bulletinBoardMasterService.getBulletinBoardMasterDTO(bulletinBoardId);
        if(ObjectUtils.isEmpty(bulletinBoardMasterDTO)){
            ApproveMasterMessageDTO masterMessageDTO = ApproveMasterMessageDTO.builder().message("존재하지 않는 전자 결제 게시글 입니다.").httpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value()).build();
            return ResponseEntity.status(masterMessageDTO.getHttpStatus()).body(masterMessageDTO);
        }
        ApproveMasterListCustomDTO result = approveMasterService.getInfiniteScrollList(bulletinBoardMasterDTO, bulletinBoardId,PageRequest.of(0, size, Sort.by(Sort.Order.asc("createdDate"))));
        return ResponseEntity.ok().body(result);
    }

    @Operation(summary = "전자 결제 사인 처리")
    @PostMapping(value = "/completed-signature")
    public ResponseEntity<ResponseDTO> postCompletedSignature( @RequestBody @Valid ApproveCompletedSignatureRequestDTO approveCompletedSignatureRequestDTO){
        String userEmail = SecurityUtils.getCurrentUserLogin().orElse(null);
        /**
         * 1. 기존 게시글 조회
         * 2. 서명 가능한 상태 체크
         * 3. 중복 결제 막기
         */
        // 유저정보가 없다면 401 코드를 전송시켜서, 토큰 갱신 API 호출을 유도함.
        if (Constants.ANONYMOUSUSER.equals(userEmail)|| userEmail ==null) {
            ApproveMasterMessageDTO result = ApproveMasterMessageDTO.builder().httpStatus(HttpStatus.UNAUTHORIZED.value()).message("UNAUTHORIZED").build();
            return ResponseEntity.status(result.getHttpStatus()).body(result);
        }
        BulletinBoardMasterDTO bulletinBoardMasterDTO =  bulletinBoardMasterService.getBulletinBoardMasterDTO(approveCompletedSignatureRequestDTO.getBulletinBoardId());
        if(ObjectUtils.isEmpty(bulletinBoardMasterDTO)){
            ApproveMasterMessageDTO masterMessageDTO = ApproveMasterMessageDTO.builder().message("존재하지 않는 전자 결제 게시글 입니다.").httpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value()).build();
            return ResponseEntity.status(masterMessageDTO.getHttpStatus()).body(masterMessageDTO);
        }
        UserMasterDTO userMasterDTO = userMasterService.getUserMasterByEmail(userEmail);
        List<ApproveMasterDTO> approveMasterDTOList = approveMasterService.userMasterByExistingApproveMastersOnTheBulletinBoard(bulletinBoardMasterDTO.getId(), userMasterDTO.getId());
        if(!ObjectUtils.isEmpty(approveMasterDTOList)){
            ApproveMasterMessageDTO masterMessageDTO = ApproveMasterMessageDTO.builder().message("이미 전자 결제를 완료했습니다.").httpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value()).build();
            return ResponseEntity.status(masterMessageDTO.getHttpStatus()).body(masterMessageDTO);
        }
        ApproveMasterDTO result = approveMasterService.addApproveMaster(approveCompletedSignatureRequestDTO.getBulletinBoardId(),userMasterDTO);

        if(ObjectUtils.isEmpty(result)){
            ApproveMasterMessageDTO masterMessageDTO = ApproveMasterMessageDTO.builder().message("전자 결제 승인 처리에 실패 했습니다.").httpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value()).build();
            return ResponseEntity.status(masterMessageDTO.getHttpStatus()).body(masterMessageDTO);
        }
        ApproveMasterMessageDTO masterMessageDTO = ApproveMasterMessageDTO.builder().message("전자 결제 승인 완료").httpStatus(HttpStatus.OK.value()).build();
        return ResponseEntity.status(masterMessageDTO.getHttpStatus()).body(masterMessageDTO);
    }
    @Operation(summary = "전자 결제 내역 내보내기")
    @GetMapping("/export-excel/{bulletinBoardId}")
    public ResponseEntity<byte[]> exportToExcel(@PathVariable String bulletinBoardId) throws IOException {

        String userEmail = SecurityUtils.getCurrentUserLogin().orElse(null);
        // 유저정보가 없다면 401 코드를 전송시켜서, 토큰 갱신 API 호출을 유도함.
        if (Constants.ANONYMOUSUSER.equals(userEmail)|| userEmail ==null) {
            ApproveMasterMessageDTO result = ApproveMasterMessageDTO.builder().httpStatus(HttpStatus.UNAUTHORIZED.value()).message("UNAUTHORIZED").build();
            return ResponseEntity.status(result.getHttpStatus()).body(null);
        }
        BulletinBoardMasterDTO bulletinBoardMasterDTO =  bulletinBoardMasterService.getBulletinBoardMasterDTO(bulletinBoardId);
        List<ApproveMasterCustomDTO> approveList = approveMasterService.existingApproveMastersOnTheBulletinBoard(bulletinBoardMasterDTO);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("전자결제 완료 리스트");

        // **헤더 생성**
        Row headerRow = sheet.createRow(0);
        String[] headers = {"문서명", "이름", "서명", "시간", "현장명"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // **데이터 추가**
        int rowIdx = 1;
        for (ApproveMasterCustomDTO approve : approveList) {
            Row row = sheet.createRow(rowIdx);
            row.createCell(0).setCellValue(approve.getConstructionBusiness());
            row.createCell(1).setCellValue(approve.getUserName());
            row.createCell(3).setCellValue(approve.getCreatedDateFormat());
            row.createCell(4).setCellValue(approve.getCompanyName());

            // 서명 이미지 삽입
            try {
                InputStream inputStream = new URL(domain + baseUrl+ "/attach/download/"+approve.getAttachDocId()).openStream();
                byte[] imageBytes = inputStream.readAllBytes();
                inputStream.close();

                int pictureIdx = workbook.addPicture(imageBytes, Workbook.PICTURE_TYPE_PNG);
                CreationHelper helper = workbook.getCreationHelper();
                Drawing drawing = sheet.createDrawingPatriarch();
                ClientAnchor anchor = helper.createClientAnchor();
                // 서명이 들어갈 컬럼과 행 지정
                anchor.setCol1(2); // 서명 컬럼 (C열)
                anchor.setRow1(rowIdx); // 해당 행

                // 한 개의 셀 크기에 맞게 이미지 크기 조절
                anchor.setCol2(3); // 다음 컬럼으로 이미지 크기 제한
                anchor.setRow2(rowIdx + 1); // 다음 행으로 이미지 크기 제한

                Picture pict = drawing.createPicture(anchor, pictureIdx);
                pict.resize(0.8); // 셀 크기에 맞게 적절한 비율 조정 (0.8 정도가 적절함)
            } catch (Exception e) {
                row.createCell(2).setCellValue("서명 이미지 없음");
            }

            rowIdx++;
        }
        // **엑셀 파일을 바이트 배열로 변환**
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        byte[] excelBytes = outputStream.toByteArray();
        outputStream.close();

        // **HTTP 응답 설정**
        HttpHeaders headersResponse = new HttpHeaders();
        headersResponse.set("Content-Disposition", "attachment; filename=\""+URLEncoder.encode(bulletinBoardMasterDTO.getBoardTitle(), StandardCharsets.UTF_8)+".xlsx\"");
        headersResponse.setContentType(org.springframework.http.MediaType.APPLICATION_OCTET_STREAM);

        return new ResponseEntity<>(excelBytes, headersResponse, HttpStatus.OK);
    }
}

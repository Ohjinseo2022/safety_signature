package com.safety_signature.safety_signature_back.app.approve_master.service.impl;

import com.safety_signature.safety_signature_back.app.approve_master.domain.ApproveMaster;
import com.safety_signature.safety_signature_back.app.approve_master.dto.ApproveMasterDTO;
import com.safety_signature.safety_signature_back.app.approve_master.dto.response.ApproveMasterCustomDTO;
import com.safety_signature.safety_signature_back.app.approve_master.dto.response.ApproveMasterListCustomDTO;
import com.safety_signature.safety_signature_back.app.approve_master.mapper.ApproveMasterMapper;
import com.safety_signature.safety_signature_back.app.approve_master.repository.ApproveMasterRepository;
import com.safety_signature.safety_signature_back.app.approve_master.service.ApproveMasterService;
import com.safety_signature.safety_signature_back.app.bulletin_board.domain.BulletinBoardMaster;
import com.safety_signature.safety_signature_back.app.bulletin_board.dto.BulletinBoardMasterDTO;
import com.safety_signature.safety_signature_back.app.common.dto.util.InfiniteScrollResponseDTO;
import com.safety_signature.safety_signature_back.app.company.dto.CompanyMasterDTO;
import com.safety_signature.safety_signature_back.app.company.dto.CompanyMemberDTO;
import com.safety_signature.safety_signature_back.app.company.repository.CompanyMasterRepository;
import com.safety_signature.safety_signature_back.app.company.repository.CompanyMemberRepository;
import com.safety_signature.safety_signature_back.app.company.service.CompanyMasterService;
import com.safety_signature.safety_signature_back.app.company.service.CompanyMemberService;
import com.safety_signature.safety_signature_back.app.user.dto.UserMasterDTO;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ApproveMasterServiceImpl implements ApproveMasterService {
    private final Logger log = LoggerFactory.getLogger(ApproveMasterServiceImpl.class);
    private final ApproveMasterRepository approveMasterRepository;
    private final ApproveMasterMapper approveMasterMapper;
    private final CompanyMemberService companyMemberService;
    private final CompanyMasterService companyMasterService;

    @Value("${api.domain}")
    private String domain;
    @Value("${api.baseUrl}")
    private String baseUrl;
    public ApproveMasterServiceImpl(ApproveMasterRepository approveMasterRepository,
                                    ApproveMasterMapper approveMasterMapper,
                                    CompanyMemberService companyMemberService, CompanyMasterService companyMasterService
    ) {
        this.approveMasterRepository = approveMasterRepository;
        this.approveMasterMapper = approveMasterMapper;
        this.companyMemberService = companyMemberService;

        this.companyMasterService = companyMasterService;
    }

    @Override
    public ApproveMasterDTO addApproveMaster(String bulletinBoardId, UserMasterDTO userMasterDTO) {
        ApproveMasterDTO approveMasterDTO = ApproveMasterDTO.builder()
                .userMasterId(userMasterDTO.getId())
                .userName(userMasterDTO.getName())
                .attachDocId(userMasterDTO.getSignatureDocId())
                .bulletinBoardId(bulletinBoardId)
                .approveStatus("approve")
                .build();
        return this.save(approveMasterDTO);
    }

    @Override
    public Page<ApproveMasterCustomDTO> getAllApproveMasters(BulletinBoardMasterDTO bulletinBoardMasterDTO, Pageable pageable) {
        Page<ApproveMaster> approveMasters = approveMasterRepository.findByBulletinBoardId(bulletinBoardMasterDTO.getId(),pageable);
        List<ApproveMasterCustomDTO> list =this.getApproveMasterCustomDTO(approveMasters, bulletinBoardMasterDTO);
        return  new PageImpl<>(list,pageable,approveMasterRepository.countByBulletinBoardId(bulletinBoardMasterDTO.getId()));
    }
    public List<ApproveMasterCustomDTO> getApproveMasterCustomDTO(Page<ApproveMaster> approveMasters , BulletinBoardMasterDTO bulletinBoardMasterDTO) {
        List<ApproveMasterCustomDTO> list = new ArrayList<>();
        for(ApproveMaster entity : approveMasters) {
            ApproveMasterDTO dto = approveMasterMapper.toDto(entity);
            CompanyMemberDTO companyMemberDTO =companyMemberService.getCompanyMemberByUserMasterId(dto.getUserMasterId());

            ApproveMasterCustomDTO customDTO = ApproveMasterCustomDTO.from(dto);
            customDTO.setApproveStatus("approve");
            customDTO.setConstructionBusiness("직접 입력");
            customDTO.setCompanyName("직접입력");
            if(companyMemberDTO != null) {
                customDTO.setConstructionBusiness(companyMemberDTO.getConstructionBusiness());
                CompanyMasterDTO companyMasterDTO = companyMasterService.getCompanyMaster(companyMemberDTO.getCompanyMasterId());
                if(companyMasterDTO != null) {
                    customDTO.setCompanyName(companyMasterDTO.getCompanyName());
                }
            }

            list.add(customDTO);
        }
        return list;
    }

    @Override
    public Optional<ApproveMasterDTO> partialUpdate(ApproveMasterDTO approveMasterDTO) {
        log.debug("Request partialUpdate(ApproveMasterDTO : {}", approveMasterDTO);
        return approveMasterRepository.findById(approveMasterDTO.getId())
                .map(existingApproveMaster->{
                    approveMasterMapper.partialUpdate(existingApproveMaster,approveMasterDTO);
                    return existingApproveMaster;
                })
                .map(approveMasterRepository::save)
                .map(approveMasterMapper::toDto);
    }

    @Override
    public ApproveMasterDTO save(ApproveMasterDTO approveMasterDTO) {
        log.debug("Request to save ApproveMasterDTO : {}", approveMasterDTO);
        return approveMasterMapper.toDto(approveMasterRepository.save(approveMasterMapper.toEntity(approveMasterDTO)));
    }

    @Override
    public List<ApproveMasterDTO> userMasterByExistingApproveMastersOnTheBulletinBoard(String bulletinBoardId, String userMasterId) {
        List<ApproveMaster> approveMaster = approveMasterRepository.findAllByBulletinBoardIdAndUserMasterId(bulletinBoardId, userMasterId);
        return approveMaster.stream().map(approveMasterMapper::toDto).toList();
    }

    @Override
    public List<ApproveMasterCustomDTO> existingApproveMastersOnTheBulletinBoard(BulletinBoardMasterDTO bulletinBoardMasterDTO) {
        List<ApproveMaster> approveMaster = approveMasterRepository.findAllByBulletinBoardIdOrderByCreatedDateAsc(bulletinBoardMasterDTO.getId());
        return approveMaster.stream().map(approveMasterMapper::toDto).map(e->
        {
            ApproveMasterCustomDTO customDTO = ApproveMasterCustomDTO.from(e);
            CompanyMemberDTO companyMemberDTO =companyMemberService.getCompanyMemberByUserMasterId(e.getUserMasterId());
            CompanyMasterDTO companyMasterDTO = companyMasterService.getCompanyMaster(companyMemberDTO.getCompanyMasterId());
            customDTO.setApproveStatus("approve");
            customDTO.setConstructionBusiness("직접 입력");
            customDTO.setCompanyName("직접입력");
            if(companyMemberDTO != null) {
                customDTO.setConstructionBusiness(companyMemberDTO.getConstructionBusiness());
            }
            if(companyMasterDTO != null) {
                customDTO.setCompanyName(companyMasterDTO.getCompanyName());
            }
            return customDTO;
        }).toList();
    }

    @Override
    public byte[] createApproveMasterDocx( BulletinBoardMasterDTO bulletinBoardMasterDTO, List<ApproveMasterCustomDTO> approveList) throws IOException {
// 강제로 approveList의 크기를 120개로 확장
        if (approveList.size() < 120) {
            List<ApproveMasterCustomDTO> expandedList = new ArrayList<>(approveList);
            while (expandedList.size() < 120) {
                for (ApproveMasterCustomDTO item : approveList) {
                    if (expandedList.size() >= 120) break;
                    expandedList.add(item);
                }
            }
            approveList = expandedList;
        }
//        0K2BQM67MTJ6K
        // **📌 기존 .docx 템플릿 파일 불러오기**
        InputStream existingStream = new URL(domain + baseUrl + "/attach/download/" + "0K2BQM67MTJ6K").openStream();
        XWPFDocument document = new XWPFDocument(existingStream);
//        bulletinBoardMasterDTO.getSiteAddress();

        // 📌 첫 번째 테이블 가져오기
        List<XWPFTable> tables = document.getTables();
        XWPFTable table = tables.get(0);


        int rowIdx = 4; // 데이터 삽입 시작 행
        int maxRowsPerBlock = 23; // 한 블록당 최대 23개
        boolean useLeftColumn = true; // A~F(0), G~K(6) 번갈아가며 사용
        int columnOffset = 0;

        for (int i = 0; i < approveList.size(); i++) {
            ApproveMasterCustomDTO approve = approveList.get(i);

            // 23개마다 컬럼 변경
            if (i > 0 && i % maxRowsPerBlock == 0) {
                useLeftColumn = !useLeftColumn;
                columnOffset = useLeftColumn ? 0 : 6;
                rowIdx++; // 다음 행으로 이동

                // A~F로 돌아올 때(A4-K4 헤더 추가)
                if (useLeftColumn) {
                    rowIdx++; // 헤더 삽입으로 한 줄 밀기
                    XWPFTableRow sourceHeaderRow = table.getRow(3); // A4~K4 헤더 행 가져오기
                    XWPFTableRow newHeaderRow = table.createRow();

                    for (int c = 0; c < 11; c++) { // A~K 컬럼 범위
                        XWPFTableCell cell = newHeaderRow.createCell();
                        if (sourceHeaderRow.getCell(c) != null) {
                            setCellText(cell, sourceHeaderRow.getCell(c).getText(), 12, true);
                        }
                    }
                }
            }

            XWPFTableRow row = table.getRow(rowIdx);
            if (row == null) row = table.createRow();

            // 셀 생성 및 기존 데이터 삭제 후 입력
            for (int j = 0; j < 5; j++) {
                XWPFTableCell cell = row.getCell(j + columnOffset);
                if (cell == null) cell = row.createCell();
                clearCell(cell);
            }

            // 데이터 입력
            setCellText(row.getCell(0 + columnOffset), String.valueOf(i + 1), 10, false);
            setCellText(row.getCell(1 + columnOffset), approve.getConstructionBusiness(), 10, false);
            setCellText(row.getCell(2 + columnOffset), "공종 데이터 필요", 10, false);
            setCellText(row.getCell(3 + columnOffset), approve.getUserName(), 10, false);

            // 서명 이미지 삽입
            try {
                InputStream inputStream = new URL(domain + baseUrl + "/attach/download/" + approve.getAttachDocId()).openStream();
                byte[] imageBytes = inputStream.readAllBytes();
                inputStream.close();

                XWPFTableCell signatureCell = row.getCell(4 + columnOffset);
                XWPFParagraph paragraph = signatureCell.addParagraph();
                paragraph.setAlignment(ParagraphAlignment.CENTER);
                XWPFRun run = paragraph.createRun();

                InputStream imageStream = new ByteArrayInputStream(imageBytes);
                run.addPicture(imageStream, Document.PICTURE_TYPE_PNG, "signature.png", Units.toEMU(80), Units.toEMU(40));
                imageStream.close();

                signatureCell.removeParagraph(0);
            } catch (Exception e) {
                setCellText(row.getCell(4 + columnOffset), "서명 없음", 10, false);
            }

            rowIdx++;
        }


        // 📌 Word 파일을 바이트 배열로 변환
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        document.write(outputStream);
        document.close();
        outputStream.close();
        return outputStream.toByteArray();
    }

    @Override
    public ApproveMasterListCustomDTO getInfiniteScrollList(BulletinBoardMasterDTO bulletinBoardMasterDTO,String bulletinBoardId, Pageable pageable) {
        /**
         * 1. 23개 단위로 데이터가 끝날떄까지 전체조회
         * 2. 23개 23개 짝에 맞게 ApproveMasterCustomDTO 데이터를 만드는데 두개를 만든다.
         * 3.
         */

        Page<ApproveMaster> page;
        String nextCursor;
        ApproveMasterListCustomDTO result = null;
        List<List<ApproveMasterCustomDTO>> evenGroupedData = new ArrayList<>(); //0 포함 짝수 페이지
        List<List<ApproveMasterCustomDTO>> oddGroupedData = new ArrayList<>(); //홀수 페이지

        boolean isOdd=false;
        do {
            // 최초 1 회 동작 0 번째 데이터 저장
            page = approveMasterRepository.findByBulletinBoardId(bulletinBoardId,pageable);
            List<ApproveMasterCustomDTO> list =this.getApproveMasterCustomDTO(page, bulletinBoardMasterDTO);
            this.addToGroupedData(evenGroupedData , list);
            isOdd = true;
            nextCursor = page.hasNext() ? page.getContent().get(page.getContent().size() - 1).getId().toString() : null;
        }
        while (nextCursor !=null);{
            page = approveMasterRepository.findByIdLessThanAndBulletinBoardId(nextCursor,bulletinBoardId,pageable);
            List<ApproveMasterCustomDTO> list =this.getApproveMasterCustomDTO(page, bulletinBoardMasterDTO);
            nextCursor = page.hasNext() ? page.getContent().get(page.getContent().size() - 1).getId().toString() : null;
            if(isOdd){
                this.addToGroupedData(oddGroupedData , list);
                isOdd = false;
            }else{
                this.addToGroupedData(evenGroupedData , list);
                isOdd = true;
            }

        };

//        String nextCursor = page.hasNext() ? page.getContent().get(page.getContent().size() - 1).getId().toString() : null;
        return  result.builder().evenData(evenGroupedData).oddData(oddGroupedData).build();
    }
    public void addToGroupedData(List<List<ApproveMasterCustomDTO>> groupedData, List<ApproveMasterCustomDTO> data) {
        if (groupedData.isEmpty() || groupedData.get(groupedData.size() - 1) == null) {
            groupedData.add(new ArrayList<>()); // 새로운 리스트 추가
        }
        groupedData.get(groupedData.size() - 1).addAll(data); // 마지막 리스트에 데이터 추가
    }
    /**
     * 📌 셀 텍스트 설정 메서드 (폰트 크기 및 Bold 설정)
     */
    private void setCellText(XWPFTableCell cell, String text, int fontSize, boolean bold) {
        if (cell == null) {
            return;
        }
        cell.removeParagraph(0);
        XWPFParagraph paragraph = cell.addParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText(text);
        run.setFontSize(fontSize);
        run.setBold(bold);
    }

    /**
     * 📌 셀 내용 삭제 메서드
     */
    private void clearCell(XWPFTableCell cell) {
        if (cell != null) {
            cell.removeParagraph(0);
            cell.addParagraph();
        }
    }
}

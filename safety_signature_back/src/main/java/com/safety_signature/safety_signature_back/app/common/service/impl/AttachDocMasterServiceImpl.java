package com.safety_signature.safety_signature_back.app.common.service.impl;

import com.safety_signature.safety_signature_back.app.common.domain.AttachDocMaster;
import com.safety_signature.safety_signature_back.app.common.dto.AttachDocHistDTO;
import com.safety_signature.safety_signature_back.app.common.dto.AttachDocMasterDTO;
import com.safety_signature.safety_signature_back.app.common.dto.util.MinIOUtilsReturnDTO;
import com.safety_signature.safety_signature_back.app.common.enumeration.AttachDocOwnerClassCode;
import com.safety_signature.safety_signature_back.app.common.enumeration.OperationTypeCode;
import com.safety_signature.safety_signature_back.app.common.mapper.AttachDocMasterMapper;
import com.safety_signature.safety_signature_back.app.common.repository.AttachDocMasterRepository;
import com.safety_signature.safety_signature_back.app.common.service.AttachDocHistService;
import com.safety_signature.safety_signature_back.app.common.service.AttachDocMasterService;
import com.safety_signature.safety_signature_back.utils.MinioUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;


/**
 * Service Implementation for managing {@link com.safety_signature.safety_signature_back.app.common.domain.AttachDocMaster}.
 */
@Service
@Transactional
public class AttachDocMasterServiceImpl implements AttachDocMasterService {

    private final Logger log = LoggerFactory.getLogger(AttachDocMasterServiceImpl.class);

    private final AttachDocMasterRepository attachDocMasterRepository;
    private final AttachDocMasterMapper attachDocMasterMapper;
    private final AttachDocHistService attachDocHistService;

    private final MinioUtils minioUtils;

    public AttachDocMasterServiceImpl(
            AttachDocMasterRepository attachDocMasterRepository,
            AttachDocMasterMapper attachDocMasterMapper,
            AttachDocHistService attachDocHistService, MinioUtils minioUtils) {
        this.attachDocMasterRepository = attachDocMasterRepository;
        this.attachDocMasterMapper = attachDocMasterMapper;
        this.attachDocHistService = attachDocHistService;

        this.minioUtils = minioUtils;
    }

//    private Map<AttachDocMasterSpecification.AttachDocMasterSearchKey, Object> setCondition(
//            Boolean isAttachStorageLimitDtime,
//            LocalDateTime dateTime,
//            Boolean checkMinIODeleteYN
//    ) {
//        Map<AttachDocMasterSpecification.AttachDocMasterSearchKey, Object> searchKeys = new LinkedHashMap<>();
//
//        //첨부문서보관제한일시유무
//        if(ObjectUtils.isNotEmpty(isAttachStorageLimitDtime)) {
//            searchKeys.put(AttachDocMasterSpecification.AttachDocMasterSearchKey.IS_ATTACH_STORAGE_LIMIT_DTIME, isAttachStorageLimitDtime);
//        }
//        //첨부문서보관제한일시 확인
//        if(ObjectUtils.isNotEmpty(dateTime)) {
//            searchKeys.put(AttachDocMasterSpecification.AttachDocMasterSearchKey.ATTACH_STORAGE_LIMIT_DTIME, dateTime);
//        }
//        //MinIO삭제여부확인
//        if(ObjectUtils.isNotEmpty(checkMinIODeleteYN)) {
//            searchKeys.put(AttachDocMasterSpecification.AttachDocMasterSearchKey.CHECK_MINIO_DELETE_YN, checkMinIODeleteYN);
//        }
//
//        return searchKeys;
//    }

    @Override
    public AttachDocMasterDTO save(AttachDocMasterDTO attachDocMasterDTO) {
        log.debug("Request to save AttachDocMaster : {}", attachDocMasterDTO);
        AttachDocMaster attachDocMaster = attachDocMasterMapper.toEntity(attachDocMasterDTO);
        attachDocMaster = attachDocMasterRepository.save(attachDocMaster);
        return attachDocMasterMapper.toDto(attachDocMaster);
    }

    @Override
    public AttachDocMasterDTO attachDocMasterAndMinIoSave(List<MultipartFile> files, String userId) {
        return null;
    }

    @Override
    public AttachDocMasterDTO base64StringSignatureImageSave(String base64StringSignatureImage,  String userId) {
        try{
            MinIOUtilsReturnDTO minIOUtilsReturnDTO  = minioUtils.base64StringImageUpload(base64StringSignatureImage,userId);
            AttachDocMasterDTO attachDocMasterDTO = AttachDocMasterDTO.builder()
                    .attachDocName(minIOUtilsReturnDTO.getAttachDocName())
                    .attachDocExplain("회원 고유 전자 서명 이미지")
                    .attachDocId(userId)
                    .attachDocPosition(minIOUtilsReturnDTO.getAttachDocPosition())
                    .attachDocOwnerClassCode(AttachDocOwnerClassCode.USER_SIGNATURE)
                    .attachDocSize(minIOUtilsReturnDTO.getAttachDocSize())
                    .attachDocOwnerId(userId)
                    .build();
            AttachDocMaster attachDocMaster = attachDocMasterMapper.toEntity(attachDocMasterDTO);
            attachDocMaster = attachDocMasterRepository.save(attachDocMaster);
            return attachDocMasterMapper.toDto(attachDocMaster);
        }catch (Exception e){
            log.debug("base64StringSignatureImageSave   Exception : {}", e.getMessage());
            return null;
        }
    }

    @Override
    public AttachDocMasterDTO update(AttachDocMasterDTO attachDocMasterDTO) {
        log.debug("Request to update AttachDocMaster : {}", attachDocMasterDTO);
        AttachDocMaster attachDocMaster = attachDocMasterMapper.toEntity(attachDocMasterDTO);
        //attachDocMaster.setIsPersisted();
        attachDocMaster = attachDocMasterRepository.save(attachDocMaster);
        return attachDocMasterMapper.toDto(attachDocMaster);
    }

    @Override
    public Optional<AttachDocMasterDTO> partialUpdate(AttachDocMasterDTO attachDocMasterDTO) {
        log.debug("Request to partially update AttachDocMaster : {}", attachDocMasterDTO);

        return attachDocMasterRepository
            .findById(attachDocMasterDTO.getId())
            .map(existingAttachDocMaster -> {
                attachDocMasterMapper.partialUpdate(existingAttachDocMaster, attachDocMasterDTO);

                return existingAttachDocMaster;
            })
            .map(attachDocMasterRepository::save)
            .map(attachDocMasterMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AttachDocMasterDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AttachDocMasters");
        return attachDocMasterRepository.findAll(pageable).map(attachDocMasterMapper::toDto);
    }

//    @Override
//    public List<AttachDocMasterDTO> findByCompanyId(
//            String companyId,
//            IntroduceTypeCode introduceTypeCode,
//            AttachDocOwnerClassCode attachDocOwnerClassCode) {
//
//        List<AttachDocMasterDTO> array = new ArrayList<>();
//
//        List<AttachDocMaster> attachDocMasters =
//                attachDocMasterRepository.findByCompanyIdAndDetailItemTypeCodesAndAttachDocOwnerClassCode(
//                        companyId, introduceTypeCode, attachDocOwnerClassCode);
//
//        for(AttachDocMaster attachDocMaster : attachDocMasters) {
//
//            AttachDocMasterDTO attachDocMasterDTO = attachDocMasterMapper.toDto(attachDocMaster);
//
//            attachDocMasterDTO.setClassValue(minioUtils.setContentsCatalogClassValue(attachDocMaster.getAttachDocPosition()));
//            attachDocMasterDTO.setAttachDocPositionFilename(attachDocMaster.getAttachDocName());
//
//            array.add(attachDocMasterDTO);
//        }
//
//        return array;
//    }

//    @Override
//    public List<AttachDocMasterDTO> findByCatalogId(
//            String contentsCatalogId,
//            List<DetailItemTypeCode> detailItemTypeCodes,
//            List<AttachDocOwnerClassCode> attachDocOwnerClassCodes
//    ) {
//
//        List<AttachDocMasterDTO> array = new ArrayList<>();
//
//        List<AttachDocMaster> attachDocMasters =
//                attachDocMasterRepository.findByCatalogIdAndDetailItemTypeCodesAndAttachDocOwnerClassCodes(
//                        contentsCatalogId, detailItemTypeCodes, attachDocOwnerClassCodes);
//
//        for(AttachDocMaster attachDocMaster : attachDocMasters) {
//
//            AttachDocMasterDTO attachDocMasterDTO = attachDocMasterMapper.toDto(attachDocMaster);
//
//            attachDocMasterDTO.setClassValue(minioUtils.setContentsCatalogClassValue(attachDocMaster.getAttachDocPosition()));
//            attachDocMasterDTO.setAttachDocPositionFilename(attachDocMaster.getAttachDocName());
//
//            array.add(attachDocMasterDTO);
//        }
//
//        return array;
//    }



//    @Override
//    public DownloadResourceDTO downloadAttachDocMasterById(
//            String attachDocId,
//            String clientIpAddr
//    ) throws Exception {
//
//        DownloadResourceDTO downloadResourceDTO = new DownloadResourceDTO();
//
//        Optional<AttachDocMaster> entity = attachDocMasterRepository.findById(attachDocId);
//
//        if(entity.isPresent()) {
//
//            downloadResourceDTO.setResource(minioUtils.download(entity.get().getAttachDocPosition()));
//            downloadResourceDTO.setFileName(entity.get().getAttachDocName());
//
//            if (!downloadResourceDTO.getResource().exists() && !downloadResourceDTO.getResource().isReadable()) {
//                throw new RuntimeException("파일을 읽을 수 없습니다. : ");
//            }
//
//            // =========================================================================================================
//            // 첨부문서이력 추가 처리
//            // =========================================================================================================
//            AttachDocHistDTO attachDocHistDTO = AttachDocHistDTO.builder()
//                    .attachDocMaster(attachDocMasterMapper.toDto(entity.get()))
//                    .operationTypeCode(OperationTypeCode.DOWN_LOADING)
//                    .operatorIpAddress(clientIpAddr)
//                    .build();
//
//            attachDocHistService.save(attachDocHistDTO);
//        }
//
//        return downloadResourceDTO;
//    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AttachDocMasterDTO> findOne(String id) {
        log.debug("Request to get AttachDocMaster : {}", id);
        return attachDocMasterRepository.findById(id).map(attachDocMasterMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete AttachDocMaster : {}", id);
        attachDocMasterRepository.deleteById(id);
    }

//    @Override
//    public Optional<AttachDocMasterDTO> findTop1ByAttachDocOwnerIdOrderByCreatedDateDesc(String id) {
//        return attachDocMasterRepository
//                .findTop1ByAttachDocOwnerIdOrderByCreatedDateDesc(id)
//                .map(attachDocMasterMapper::toDto);
//    }


//    @Override
//    @Transactional(readOnly = true)
//    public List<AttachDocMasterDTO> findByCatalogIdForProviders(String contentsCatalogId, String type) {
//
//        List<AttachDocMasterDTO> array = new ArrayList<>();
//
//        List<AttachDocMaster> attachDocMasters =
//                attachDocMasterRepository.findByCatalogIdForProviders(
//                        contentsCatalogId,
//                        "main".equals(type) ? IntroduceTypeCode.ICON_MAIN_ATTACH_DOC_UUID : IntroduceTypeCode.ICON_ATTACH_DOC_UUID
//                        );
//
//        for(AttachDocMaster attachDocMaster : attachDocMasters) {
//
//            AttachDocMasterDTO attachDocMasterDTO = attachDocMasterMapper.toDto(attachDocMaster);
//            attachDocMasterDTO.setAttachDocPositionFilename(attachDocMaster.getAttachDocName());
//
//            array.add(attachDocMasterDTO);
//        }
//
//        return array;
//    }

//    @Override
//    public List<AttachDocMasterDTO> findByAttachDocOwnerId(String attachDocOwnerId) {
//        return attachDocMasterMapper.toDto(attachDocMasterRepository.findByAttachDocOwnerId(attachDocOwnerId));
//    }

//    @Override
//    public List<AttachDocMasterDTO> findByMinioDeleteYN(
//            Boolean isAttachStorageLimitDtime,
//            LocalDateTime dateTime,
//            Boolean checkMinIODeleteYNint,
//            int size) {
//
//        Map<AttachDocMasterSpecification.AttachDocMasterSearchKey, Object> searchKeys
//                = this.setCondition(true, dateTime, true);
//
//        Specification<AttachDocMaster> specs = AttachDocMasterSpecification.searchWith(searchKeys);
//
//        Pageable pageable = PageRequest.of(0, size, Sort.by(Sort.Order.asc("attachStorageLimitDtime")));
//
//
//        List<AttachDocMasterDTO> dtoList =
//                attachDocMasterRepository.findAll(specs, pageable)
//                        .stream().map(attachDocMasterMapper::toDto).toList();
//
//        return dtoList;
//    }
}

package com.safety_signature.safety_signature_back.app.bulletin_board.service.impl;

import com.safety_signature.safety_signature_back.app.approve_master.domain.ApproveMaster;
import com.safety_signature.safety_signature_back.app.approve_master.repository.ApproveMasterRepository;
import com.safety_signature.safety_signature_back.app.bulletin_board.domain.BulletinBoardMaster;
import com.safety_signature.safety_signature_back.app.bulletin_board.dto.BulletinBoardAttachInfoDTO;
import com.safety_signature.safety_signature_back.app.bulletin_board.dto.BulletinBoardMasterDTO;
import com.safety_signature.safety_signature_back.app.bulletin_board.dto.custom.BulletinBoardMasterCustomDTO;
import com.safety_signature.safety_signature_back.app.bulletin_board.dto.request.BulletinBoardRegistrationRequestDTO;
import com.safety_signature.safety_signature_back.app.bulletin_board.mapper.BulletinBoardMasterMapper;
import com.safety_signature.safety_signature_back.app.bulletin_board.repository.BulletinBoardMasterRepository;
import com.safety_signature.safety_signature_back.app.bulletin_board.service.BulletinBoardAttachInfoService;
import com.safety_signature.safety_signature_back.app.bulletin_board.service.BulletinBoardMasterService;
import com.safety_signature.safety_signature_back.app.bulletin_board.service.specification.BulletinBoardMasterServiceSpecification;
import com.safety_signature.safety_signature_back.app.common.dto.AttachDocMasterDTO;
import com.safety_signature.safety_signature_back.app.common.dto.util.InfiniteScrollResponseDTO;
import com.safety_signature.safety_signature_back.app.common.service.AttachDocMasterService;
import com.safety_signature.safety_signature_back.app.user.dto.UserMasterDTO;
import com.safety_signature.safety_signature_back.app.user.service.UserMasterService;
import com.safety_signature.safety_signature_back.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * Service Implementation for managing {@link BulletinBoardMaster}.
 */

@Service
@Transactional
public class BulletinBoardMasterServiceImpl implements BulletinBoardMasterService {
    private final Logger log = LoggerFactory.getLogger(BulletinBoardMasterServiceImpl.class);
    private UserMasterService userMasterService;
    private BulletinBoardMasterRepository bulletinBoardMasterRepository;
    private BulletinBoardMasterMapper bulletinBoardMasterMapper;
    private AttachDocMasterService attachDocMasterService;
    private BulletinBoardAttachInfoService bulletinBoardAttachInfoService;
    private ApproveMasterRepository approveMasterRepository;

    public BulletinBoardMasterServiceImpl(UserMasterService userMasterService, BulletinBoardMasterRepository bulletinBoardMasterRepository, BulletinBoardMasterMapper bulletinBoardMasterMapper, AttachDocMasterService attachDocMasterService, BulletinBoardAttachInfoService bulletinBoardAttachInfoService,
                                          ApproveMasterRepository approveMasterRepository) {
        this.userMasterService = userMasterService;
        this.bulletinBoardMasterRepository = bulletinBoardMasterRepository;
        this.bulletinBoardMasterMapper = bulletinBoardMasterMapper;
        this.attachDocMasterService = attachDocMasterService;
        this.bulletinBoardAttachInfoService = bulletinBoardAttachInfoService;
        this.approveMasterRepository = approveMasterRepository;
    }

    /**
     * @param registrationRequestDTO
     * @param files
     * @param userEmail
     * @return 전자결제 문서 MinIo 저장 및 게시글 저장 처리 후 Optional<BulletinBoardMasterDTO> 리턴
     */
    @Override
    public BulletinBoardMasterDTO saveBulletinBoardMaster(BulletinBoardRegistrationRequestDTO registrationRequestDTO, List<MultipartFile> files , String userEmail) {
        /**
         * 1.회원 ID 정보 필요함
         * 2.권한체크
         * 3.첨부파일 저장 (리스트 형태)
         * 4.첨부파일 저장 성공시 게시글 저장 처리
         */
        UserMasterDTO userMasterDTO = userMasterService.getUserMasterByEmail(userEmail);
        if(userMasterDTO == null) {
            return null;
        }
        //전자문서 게시글 저장
        BulletinBoardMasterDTO bulletinBoardMasterDTO = this.save(BulletinBoardMasterDTO.builder()
                .boardContents(registrationRequestDTO.getBoardContents())
                .boardTitle(registrationRequestDTO.getBoardTitle())
                .userMasterId(userMasterDTO.getId())
                .userMasterDTO(userMasterDTO)
                .attachYn(!ObjectUtils.isEmpty(files))//첨부 파일 유무
                .build());
        //첨부파일 저장
        List<AttachDocMasterDTO> attachDocMasterDTOList = attachDocMasterService.attachDocMasterAndMinIoSave(files, bulletinBoardMasterDTO.getId(),userMasterDTO.getId());
        //전자결제 게시글 첨부파일 정보 저장 - 필요없을 수도 있는데 일단 저장처리 나중에 조회쿼리에서 필요없으면 삭제 처리 예정
        for(AttachDocMasterDTO dto :attachDocMasterDTOList){
            bulletinBoardAttachInfoService.save( BulletinBoardAttachInfoDTO
                    .builder()
                    .attach_doc_id(dto.getId())
                    .userMasterId(userMasterDTO.getId())
                    .userMasterDTO(userMasterDTO)
                    .build());
        }
        return bulletinBoardMasterDTO;
    }

    @Override
    public BulletinBoardMasterDTO save(BulletinBoardMasterDTO bulletinBoardMasterDTO) {
        log.debug("Request to save bulletinBoardMaster : {}", bulletinBoardMasterDTO);
        return bulletinBoardMasterMapper.toDto(bulletinBoardMasterRepository
                .save(bulletinBoardMasterMapper.toEntity(bulletinBoardMasterDTO)));
    }

    @Override
    public Page<BulletinBoardMasterCustomDTO> getBulletinBoardMasterSearchConditionList(String boardTitle, String createdBy, String startDate, String endDate, String ownerId, Pageable pageable) {
        Map<BulletinBoardMasterServiceSpecification.BulletinBoardMasterSearchCondition,Object> condition = new LinkedHashMap<>();
        if(!ObjectUtils.isEmpty(boardTitle)){
            condition.put(BulletinBoardMasterServiceSpecification.BulletinBoardMasterSearchCondition.BOARD_TITLE,boardTitle);
        }
        if(!ObjectUtils.isEmpty(createdBy)){
            condition.put(BulletinBoardMasterServiceSpecification.BulletinBoardMasterSearchCondition.CREATED_BY,createdBy);
        }
        if(!ObjectUtils.isEmpty(startDate)){
            condition.put(BulletinBoardMasterServiceSpecification.BulletinBoardMasterSearchCondition.START_DATE,startDate);
        }
        if(!ObjectUtils.isEmpty(endDate)){
            condition.put(BulletinBoardMasterServiceSpecification.BulletinBoardMasterSearchCondition.END_DATE,endDate);
        }
        if(!ObjectUtils.isEmpty(ownerId)){
            condition.put(BulletinBoardMasterServiceSpecification.BulletinBoardMasterSearchCondition.USER_ID,ownerId);
        }
        Specification<BulletinBoardMaster> specs =  BulletinBoardMasterServiceSpecification.getSpecification(condition);
        List<BulletinBoardMasterCustomDTO> list = new ArrayList<>();
        Page<BulletinBoardMaster> bulletinBoardMasterList = bulletinBoardMasterRepository.findAll(specs, pageable);
        for(BulletinBoardMaster entity : bulletinBoardMasterList){
            log.info("bulletinBoardMaster : {}", entity);
            //가릴정보 안보이게 처리
            BulletinBoardMasterDTO dto = bulletinBoardMasterMapper.toDto(entity);
            dto.getUserMasterDTO().setUserPassword(null);
            //등록일시 //companyMasterDTO.getCreatedDateFormat();
            BulletinBoardMasterCustomDTO customDTO = BulletinBoardMasterCustomDTO.from(dto);
            //서명 완료 횟수 카운트 정보 추가해야함!!!!
            Long approveCount = approveMasterRepository.countByBulletinBoardId(customDTO.getId());
            log.info("approveCount : {}", approveCount);
            //서명 완료 횟수 조회가 안되면 0L 입력
            customDTO.setSignatureCount(Optional.ofNullable(approveCount).orElse(0L));
            list.add(customDTO);
            //추가적인 조회나 데이터 조작을 여기다 하면될듯 싶음 !
        }
        Page<BulletinBoardMasterCustomDTO> result =
                new PageImpl<>(list ,pageable, bulletinBoardMasterRepository.count(specs));
        return result;
    }

    @Override
    public BulletinBoardMasterCustomDTO getBulletinBoardMasterCustomDTO(String bulletinBoardId) {
        BulletinBoardMasterDTO bulletinBoardMasterDTO = bulletinBoardMasterMapper.toDto(bulletinBoardMasterRepository.findById(bulletinBoardId).orElse(null));
        if(bulletinBoardMasterDTO != null){
            List<AttachDocMasterDTO> attachDocMasterDTOList = attachDocMasterService.findByAttachDocOwnerId(bulletinBoardMasterDTO.getId());
            BulletinBoardMasterCustomDTO result =BulletinBoardMasterCustomDTO.from(bulletinBoardMasterDTO);
            result.setSignatureCount(approveMasterRepository.countByBulletinBoardId(result.getId()));
            result.setAttachDocList(attachDocMasterDTOList);
            return result;
        }
        return null;
    }

    @Override
    public BulletinBoardMasterDTO getBulletinBoardMasterDTO(String bulletinBoardId) {
        return bulletinBoardMasterMapper.toDto(bulletinBoardMasterRepository.findById(bulletinBoardId).orElse(null));
    }

    @Override
    public InfiniteScrollResponseDTO<BulletinBoardMasterCustomDTO> getInfiniteScrollData(Optional<String> cursor, Pageable pageable) {
        Page<BulletinBoardMaster> page;

        if (cursor.isPresent()) {
            page = bulletinBoardMasterRepository.findByIdLessThanOrderByCreatedDateDesc(cursor.get(), pageable);
        } else {
            page = bulletinBoardMasterRepository.findAll(pageable);
        }
        // 다음 커서 값 설정 (있으면 마지막 데이터의 ID)
        String nextCursor = page.hasNext() ? page.getContent().get(page.getContent().size() - 1).getId().toString() : null;
        List<BulletinBoardMasterCustomDTO> list = new ArrayList<>();
        //로그인된 사용자의 이메일 정보
        String userEmail = SecurityUtils.getCurrentUserLogin().orElse(null);
        // 무조건 있긴함. 처음 리소스 호출시 검증로직 선 실행 후 해당 로직에 도달됨
        UserMasterDTO userMasterDTO = userMasterService.getUserMasterByEmail(userEmail);

        for(BulletinBoardMaster entity : page){
            log.info("InfiniteScroll bulletinBoardMaster : {}", entity);
            //가릴정보 안보이게 처리
            BulletinBoardMasterDTO dto = bulletinBoardMasterMapper.toDto(entity);
            dto.getUserMasterDTO().setUserPassword(null);
            //등록일시 //companyMasterDTO.getCreatedDateFormat();
            BulletinBoardMasterCustomDTO customDTO = BulletinBoardMasterCustomDTO.from(dto);
            //서명 완료 횟수 카운트 정보 추가해야함!!!!
            Long approveCount = Optional.ofNullable(approveMasterRepository.countByBulletinBoardId(customDTO.getId())).orElse(0L);
            if(approveCount ==  0){
                customDTO.setCompletionYn(false);
            }else{
               List<ApproveMaster> approveMaster = approveMasterRepository.findAllByBulletinBoardIdAndUserMasterId(customDTO.getId(), userMasterDTO.getId());
                if (ObjectUtils.isEmpty(approveMaster)) {
                    customDTO.setCompletionYn(false);
                } else {
                    customDTO.setCompletionYn(true);
                }
            }
            log.info("approveCount : {}", approveCount);
            //서명 완료 횟수 조회가 안되면 0L 입력
            customDTO.setSignatureCount(approveCount);
            list.add(customDTO);
            //추가적인 조회나 데이터 조작을 여기다 하면될듯 싶음 !
        }
        Page<BulletinBoardMasterCustomDTO> result =
                new PageImpl<>(list ,pageable, bulletinBoardMasterRepository.count());
        return InfiniteScrollResponseDTO.from(result, nextCursor,page.hasNext());
    }
}

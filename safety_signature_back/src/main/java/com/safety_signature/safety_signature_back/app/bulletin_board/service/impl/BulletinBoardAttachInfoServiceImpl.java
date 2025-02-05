package com.safety_signature.safety_signature_back.app.bulletin_board.service.impl;

import com.safety_signature.safety_signature_back.app.bulletin_board.dto.BulletinBoardAttachInfoDTO;
import com.safety_signature.safety_signature_back.app.bulletin_board.mapper.BulletinBoardAttachInfoMapper;
import com.safety_signature.safety_signature_back.app.bulletin_board.repository.BulletinBoardAttachInfoRepository;
import com.safety_signature.safety_signature_back.app.bulletin_board.service.BulletinBoardAttachInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BulletinBoardAttachInfoServiceImpl implements BulletinBoardAttachInfoService {
    private final Logger log = LoggerFactory.getLogger(BulletinBoardMasterServiceImpl.class);
    private final BulletinBoardAttachInfoRepository bulletinBoardAttachInfoRepository;
    private final BulletinBoardAttachInfoMapper bulletinBoardAttachInfoMapper;

    public BulletinBoardAttachInfoServiceImpl(BulletinBoardAttachInfoRepository bulletinBoardAttachInfoRepository, BulletinBoardAttachInfoMapper bulletinBoardAttachInfoMapper) {
        this.bulletinBoardAttachInfoRepository = bulletinBoardAttachInfoRepository;
        this.bulletinBoardAttachInfoMapper = bulletinBoardAttachInfoMapper;
    }


    @Override
    public BulletinBoardAttachInfoDTO save(BulletinBoardAttachInfoDTO bulletinBoardAttachInfoDTO) {
        log.debug("Request to save BulletinBoardAttachInfo : {}", bulletinBoardAttachInfoDTO);
        return   bulletinBoardAttachInfoMapper.toDto(bulletinBoardAttachInfoRepository.save(bulletinBoardAttachInfoMapper.toEntity(bulletinBoardAttachInfoDTO)));
    }
}

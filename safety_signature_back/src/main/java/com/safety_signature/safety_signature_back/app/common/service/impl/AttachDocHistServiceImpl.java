package com.safety_signature.safety_signature_back.app.common.service.impl;

import com.safety_signature.safety_signature_back.app.common.domain.AttachDocHist;
import com.safety_signature.safety_signature_back.app.common.dto.AttachDocHistDTO;
import com.safety_signature.safety_signature_back.app.common.mapper.AttachDocHistMapper;
import com.safety_signature.safety_signature_back.app.common.repository.AttachDocHistRepository;
import com.safety_signature.safety_signature_back.app.common.service.AttachDocHistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

/**
 * Service Implementation for managing {@link com.safety_signature.safety_signature_back.app.common.domain.AttachDocHist}.
 */
@Service
@Transactional
public class AttachDocHistServiceImpl implements AttachDocHistService {

    private final Logger log = LoggerFactory.getLogger(AttachDocHistServiceImpl.class);

    private final AttachDocHistRepository attachDocHistRepository;

    private final AttachDocHistMapper attachDocHistMapper;

    public AttachDocHistServiceImpl(AttachDocHistRepository attachDocHistRepository, AttachDocHistMapper attachDocHistMapper) {
        this.attachDocHistRepository = attachDocHistRepository;
        this.attachDocHistMapper = attachDocHistMapper;
    }

    @Override
    public AttachDocHistDTO save(AttachDocHistDTO attachDocHistDTO) {
        log.debug("Request to save AttachDocHist : {}", attachDocHistDTO);
        AttachDocHist attachDocHist = attachDocHistMapper.toEntity(attachDocHistDTO);
        attachDocHist = attachDocHistRepository.save(attachDocHist);
        return attachDocHistMapper.toDto(attachDocHist);
    }

    @Override
    public AttachDocHistDTO update(AttachDocHistDTO attachDocHistDTO) {
        log.debug("Request to update AttachDocHist : {}", attachDocHistDTO);
        AttachDocHist attachDocHist = attachDocHistMapper.toEntity(attachDocHistDTO);
        //attachDocHist.setIsPersisted();
        attachDocHist = attachDocHistRepository.save(attachDocHist);
        return attachDocHistMapper.toDto(attachDocHist);
    }

    @Override
    public Optional<AttachDocHistDTO> partialUpdate(AttachDocHistDTO attachDocHistDTO) {
        log.debug("Request to partially update AttachDocHist : {}", attachDocHistDTO);

        return attachDocHistRepository
            .findById(attachDocHistDTO.getId())
            .map(existingAttachDocHist -> {
                attachDocHistMapper.partialUpdate(existingAttachDocHist, attachDocHistDTO);

                return existingAttachDocHist;
            })
            .map(attachDocHistRepository::save)
            .map(attachDocHistMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AttachDocHistDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AttachDocHists");
        return attachDocHistRepository.findAll(pageable).map(attachDocHistMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AttachDocHistDTO> findOne(String id) {
        log.debug("Request to get AttachDocHist : {}", id);
        return attachDocHistRepository.findById(id).map(attachDocHistMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete AttachDocHist : {}", id);
        attachDocHistRepository.deleteById(id);
    }




}

package com.safety_signature.safety_signature_back.app.common.service;

import com.safety_signature.safety_signature_back.app.common.dto.AttachDocMasterDTO;
import com.safety_signature.safety_signature_back.app.common.dto.DownloadResourceDTO;
import com.safety_signature.safety_signature_back.app.common.exception.CommonException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.safety_signature.safety_signature_back.app.common.domain.AttachDocMaster}.
 */
public interface AttachDocMasterService {
    /**
     * Save a attachDocMaster.
     *
     * @param attachDocMasterDTO the entity to save.
     * @return the persisted entity.
     */
    AttachDocMasterDTO save(AttachDocMasterDTO attachDocMasterDTO);
    DownloadResourceDTO downloadAttachDocMasterById(
            String attachDocMasterId,
            String clientIpAddr
    )throws Exception;
    List<AttachDocMasterDTO> attachDocMasterAndMinIoSave(List<MultipartFile> files, String attachDocOwnerId,String userId) ;
    AttachDocMasterDTO base64StringSignatureImageSave(String base64StringSignatureImage , String userId);
    /**
     * Updates a attachDocMaster.
     *
     * @param attachDocMasterDTO the entity to update.
     * @return the persisted entity.
     */
    AttachDocMasterDTO update(AttachDocMasterDTO attachDocMasterDTO);

    /**
     * Partially updates a attachDocMaster.
     *
     * @param attachDocMasterDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AttachDocMasterDTO> partialUpdate(AttachDocMasterDTO attachDocMasterDTO);

    List<AttachDocMasterDTO> findByAttachDocOwnerId(String attachDocOwnerId);
    /**
     * Get all the attachDocMasters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AttachDocMasterDTO> findAll(Pageable pageable);

    /**
     * Get the "id" attachDocMaster.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AttachDocMasterDTO> findOne(String id);

    /**
     * Delete the "id" attachDocMaster.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

}

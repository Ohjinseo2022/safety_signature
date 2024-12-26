package com.safety_signature.safety_signature_back.app.common.service;

import com.safety_signature.safety_signature_back.app.common.dto.AttachDocHistDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.safety_signature.safety_signature_back.app.common.domain.AttachDocHist}.
 */
public interface AttachDocHistService {
    /**
     * Save a attachDocHist.
     *
     * @param attachDocHistDTO the entity to save.
     * @return the persisted entity.
     */
    AttachDocHistDTO save(AttachDocHistDTO attachDocHistDTO);

    /**
     * Updates a attachDocHist.
     *
     * @param attachDocHistDTO the entity to update.
     * @return the persisted entity.
     */
    AttachDocHistDTO update(AttachDocHistDTO attachDocHistDTO);

    /**
     * Partially updates a attachDocHist.
     *
     * @param attachDocHistDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AttachDocHistDTO> partialUpdate(AttachDocHistDTO attachDocHistDTO);

    /**
     * Get all the attachDocHists.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AttachDocHistDTO> findAll(Pageable pageable);

    /**
     * Get the "id" attachDocHist.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AttachDocHistDTO> findOne(String id);

    /**
     * Delete the "id" attachDocHist.
     *
     * @param id the id of the entity.
     */
    void delete(String id);



}

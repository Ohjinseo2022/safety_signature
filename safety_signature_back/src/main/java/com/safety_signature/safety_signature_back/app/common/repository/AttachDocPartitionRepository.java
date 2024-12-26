package com.safety_signature.safety_signature_back.app.common.repository;

import com.safety_signature.safety_signature_back.app.common.domain.AttachDocPartition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the ContentsBuyMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttachDocPartitionRepository extends JpaRepository<AttachDocPartition, String>, JpaSpecificationExecutor<AttachDocPartition> {

    List<AttachDocPartition> findByAttachDocMaster_IdOrderByPageTurn(String AttachDocId);
}

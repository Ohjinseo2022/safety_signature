package com.safety_signature.safety_signature_back.app.common.repository;

import com.safety_signature.safety_signature_back.app.common.domain.AttachDocMaster;
import com.safety_signature.safety_signature_back.app.common.enumeration.AttachDocOwnerClassCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the AttachDocMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttachDocMasterRepository extends JpaRepository<AttachDocMaster, String>, JpaSpecificationExecutor<AttachDocMaster> {

    List<AttachDocMaster> findByAttachDocOwnerId(String attachDocOwnerId);

    List<AttachDocMaster> findByAttachDocOwnerIdAndAttachDocOwnerClassCode(String attachDocOwnerId, AttachDocOwnerClassCode attachDocOwnerClassCode);

    List<AttachDocMaster> findByAttachDocOwnerClassCode(AttachDocOwnerClassCode attachDocOwnerClassCode);

    Long deleteByAttachDocOwnerId(String attachDocOwnerId);

    List<AttachDocMaster> findAllByIdInOrderByCreatedDateAsc(List<String> attachDocIds);

}

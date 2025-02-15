package com.safety_signature.safety_signature_back.app.approve_master.repository;

import com.safety_signature.safety_signature_back.app.approve_master.domain.ApproveMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ApproveMasterRepository extends JpaRepository<ApproveMaster, String> , JpaSpecificationExecutor<ApproveMaster> {
    long countByBulletinBoardId(String bulletinBoardId);
}

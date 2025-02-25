package com.safety_signature.safety_signature_back.app.approve_master.repository;

import com.safety_signature.safety_signature_back.app.approve_master.domain.ApproveMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ApproveMasterRepository extends JpaRepository<ApproveMaster, String> , JpaSpecificationExecutor<ApproveMaster> {
    long countByBulletinBoardId(String bulletinBoardId);
    Page<ApproveMaster> findByBulletinBoardId(String bulletinBoardId, Pageable page);
    List<ApproveMaster> findAllByBulletinBoardId(String bulletinBoardId);
}

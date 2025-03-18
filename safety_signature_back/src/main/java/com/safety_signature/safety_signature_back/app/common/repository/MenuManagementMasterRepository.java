package com.safety_signature.safety_signature_back.app.common.repository;

import com.safety_signature.safety_signature_back.app.common.domain.MenuManagementMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuManagementMasterRepository extends JpaRepository<MenuManagementMaster, String> , JpaSpecificationExecutor<MenuManagementMaster> {
}

package com.safety_signature.safety_signature_back.app.company.repository;

import com.safety_signature.safety_signature_back.app.company.domain.CompanyMaster;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface CompanyMasterRepository extends CrudRepository<CompanyMaster, String>, JpaSpecificationExecutor<CompanyMaster> {
}

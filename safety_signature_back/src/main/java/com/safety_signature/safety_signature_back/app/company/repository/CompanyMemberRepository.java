package com.safety_signature.safety_signature_back.app.company.repository;

import com.safety_signature.safety_signature_back.app.company.domain.CompanyMember;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@SuppressWarnings("unused")
@Repository
public interface CompanyMemberRepository extends CrudRepository<CompanyMember, String>, JpaSpecificationExecutor<CompanyMember> {
    Optional<CompanyMember> findByUserMasterId(String UserMasterId);
}

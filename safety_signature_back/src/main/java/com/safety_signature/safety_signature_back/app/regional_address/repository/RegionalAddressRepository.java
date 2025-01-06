package com.safety_signature.safety_signature_back.app.regional_address.repository;

import com.safety_signature.safety_signature_back.app.regional_address.domain.RegionalAddress;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@SuppressWarnings("unused")
@Repository
public interface RegionalAddressRepository extends CrudRepository<RegionalAddress,String>, JpaSpecificationExecutor<RegionalAddress> {
}

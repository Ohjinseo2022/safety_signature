package com.safety_signature.safety_signature_back.app.company.service.impl;

import com.safety_signature.safety_signature_back.app.company.domain.CompanyMaster;
import com.safety_signature.safety_signature_back.app.company.dto.CompanyMasterDTO;
import com.safety_signature.safety_signature_back.app.company.mapper.CompanyMasterMapper;
import com.safety_signature.safety_signature_back.app.company.repository.CompanyMasterRepository;
import com.safety_signature.safety_signature_back.app.company.service.CompanyMasterService;
import org.springframework.stereotype.Service;

@Service
public class CompanyMasterServiceImpl implements CompanyMasterService {
    private CompanyMasterRepository companyMasterRepository;
    private CompanyMasterMapper companyMasterMapper;

    public CompanyMasterServiceImpl(CompanyMasterRepository companyMasterRepository, CompanyMasterMapper companyMasterMapper) {
        this.companyMasterRepository = companyMasterRepository;
        this.companyMasterMapper = companyMasterMapper;
    }

    @Override
    public CompanyMasterDTO getCompanyMaster(String Id) {
        CompanyMaster companyMaster = companyMasterRepository.findById(Id).orElse(null);
        if(companyMaster == null) {
            return null;
        }
        return companyMasterMapper.toDto(companyMaster);
    }
}

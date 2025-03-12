package com.safety_signature.safety_signature_back.app.company.service.impl;

import com.safety_signature.safety_signature_back.app.company.domain.CompanyMember;
import com.safety_signature.safety_signature_back.app.company.dto.CompanyMemberDTO;
import com.safety_signature.safety_signature_back.app.company.mapper.CompanyMemberMapper;
import com.safety_signature.safety_signature_back.app.company.repository.CompanyMemberRepository;
import com.safety_signature.safety_signature_back.app.company.service.CompanyMemberService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

@Service
public class CompanyMemberServiceImpl implements CompanyMemberService {
    private CompanyMemberRepository companyMemberRepository;
    private CompanyMemberMapper companyMemberMapper;
    public CompanyMemberServiceImpl(CompanyMemberRepository companyMemberRepository,
                                    CompanyMemberMapper companyMemberMapper) {
        this.companyMemberRepository = companyMemberRepository;
        this.companyMemberMapper = companyMemberMapper;
    }
    @Override
    public CompanyMemberDTO getCompanyMemberByUserMasterId(String userMasterId) {
        CompanyMember companyMember =  companyMemberRepository.findByUserMasterId(userMasterId).orElse(null);
        if(ObjectUtils.isEmpty(companyMember)){
            return null;
        }else{
            return companyMemberMapper.toDto(companyMember);
        }
    }
}

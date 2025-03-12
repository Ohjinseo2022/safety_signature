package com.safety_signature.safety_signature_back.app.company.service;

import com.safety_signature.safety_signature_back.app.company.dto.CompanyMemberDTO;

public interface CompanyMemberService {
    CompanyMemberDTO getCompanyMemberByUserMasterId(String userMasterId);
}

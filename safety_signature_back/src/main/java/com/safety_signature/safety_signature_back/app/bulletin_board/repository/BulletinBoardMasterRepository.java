package com.safety_signature.safety_signature_back.app.bulletin_board.repository;

import com.safety_signature.safety_signature_back.app.bulletin_board.domain.BulletinBoardMaster;
import com.safety_signature.safety_signature_back.app.common.enumeration.SafetySignatureStatusCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
@Repository
public interface BulletinBoardMasterRepository extends JpaRepository<BulletinBoardMaster, String> , JpaSpecificationExecutor<BulletinBoardMaster> {
    Optional<BulletinBoardMaster> findById(String id);
    /**
     * 커서 이후의 데이터를 조회
     */
    Page<BulletinBoardMaster> findByIdLessThanAndBoardStatusCodeOrderByCreatedDateDesc(String cursor, SafetySignatureStatusCode boardStatusCode, Pageable pageable);

    /**
     * 첫 페이지 데이터 조회
     */
    Page<BulletinBoardMaster> findAllByBoardStatusCode(SafetySignatureStatusCode boardStatusCode,Pageable pageable);


}

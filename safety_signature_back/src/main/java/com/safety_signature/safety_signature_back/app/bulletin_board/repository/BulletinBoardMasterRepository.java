package com.safety_signature.safety_signature_back.app.bulletin_board.repository;

import com.safety_signature.safety_signature_back.app.bulletin_board.domain.BulletinBoardMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface BulletinBoardMasterRepository extends JpaRepository<BulletinBoardMaster, String> , JpaSpecificationExecutor<BulletinBoardMaster> {
}

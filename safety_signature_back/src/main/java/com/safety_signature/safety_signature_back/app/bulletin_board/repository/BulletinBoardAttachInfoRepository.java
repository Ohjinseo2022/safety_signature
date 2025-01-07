package com.safety_signature.safety_signature_back.app.bulletin_board.repository;

import com.safety_signature.safety_signature_back.app.bulletin_board.domain.BulletinBoardAttachInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface BulletinBoardAttachInfoRepository extends JpaRepository<BulletinBoardAttachInfo, String>, JpaSpecificationExecutor<BulletinBoardAttachInfo> {
}

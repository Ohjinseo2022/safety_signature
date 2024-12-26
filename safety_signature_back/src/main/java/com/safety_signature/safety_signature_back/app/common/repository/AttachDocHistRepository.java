package com.safety_signature.safety_signature_back.app.common.repository;

import com.safety_signature.safety_signature_back.app.common.domain.AttachDocHist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the AttachDocHist entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttachDocHistRepository extends JpaRepository<AttachDocHist, String>, JpaSpecificationExecutor<AttachDocHist> {

    Optional<AttachDocHist> findByAttachDocUuid(String attachDocUuid);

//    Optional<AttachDocHist> findById(String contentsCatalogId);
    
//    @Query(value =
//			" select count(distinct a.id) cnt from tb_attach_doc_hist  a "+
//			" inner join tb_attach_doc_master b on a.attach_doc_uuid = b.id  "+
//			" and b.attach_doc_owner_class_code = 'CONTENTS_DOCUMENT' "+
//			" inner join tb_contents_buy_master c on contents_catalog_id = :id "+
//			" and c.id = b.attach_doc_owner_id  " +
//			" where a.operation_type_code = 'DOWN_LOADING'  "
//            , nativeQuery = true)
//    Integer getDownloadCountByContentsCatalogId(String id);
//
//    @Query(value =
//			" select count(distinct a.id) cnt from tb_attach_doc_hist  a "+
//			" inner join tb_attach_doc_master b on a.attach_doc_uuid = b.id  "+
//			" and b.attach_doc_owner_class_code = 'CONTENTS_SUBSCRIBE_DOC' "+
//			" inner join tb_contents_buy_master c on contents_catalog_id = :id "+
//			" inner join tb_contents_subscribe_hist d on c.id = d.contents_buy_id  "+
//			" and d.id = b.attach_doc_owner_id  " +
//			" where a.operation_type_code = 'DOWN_LOADING'  "
//            , nativeQuery = true)
//    Integer getDownloadCountByContentsCatalogIdSubscribe(String id);
}

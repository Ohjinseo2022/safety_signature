package com.safety_signature.safety_signature_back.app.common.repository;

import com.safety_signature.safety_signature_back.app.common.domain.AttachDocMaster;
import com.safety_signature.safety_signature_back.app.common.enumeration.AttachDocOwnerClassCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the AttachDocMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttachDocMasterRepository extends JpaRepository<AttachDocMaster, String>, JpaSpecificationExecutor<AttachDocMaster> {

    List<AttachDocMaster> findByAttachDocOwnerId(String attachDocOwnerId);

    List<AttachDocMaster> findByAttachDocOwnerIdAndAttachDocOwnerClassCode(String attachDocOwnerId, AttachDocOwnerClassCode attachDocOwnerClassCode);

    List<AttachDocMaster> findByAttachDocOwnerClassCode(AttachDocOwnerClassCode attachDocOwnerClassCode);

//    @Query(     " SELECT doc"
//            +   " FROM CompanyMaster company "
//            +           " INNER JOIN AffiliateIntroduce affiliate "
//            +                   " ON company.id = affiliate.companyMaster.id"
//            +           " INNER JOIN AttachDocMaster doc "
//            +                   " ON affiliate.id = doc.attachDocOwnerId"
//            +   " WHERE company.id = :companyId "
//            +           " AND affiliate.introduceTypeCode = :#{#introduceTypeCode} "
//            +           " AND doc.attachDocOwnerClassCode = :#{#attachDocOwnerClassCode} "
//    )
//    List<AttachDocMaster> findByCompanyIdAndDetailItemTypeCodesAndAttachDocOwnerClassCode(
//            @Param("companyId") String companyId,
//            @Param("introduceTypeCode") IntroduceTypeCode introduceTypeCode,
//            @Param("attachDocOwnerClassCode") AttachDocOwnerClassCode attachDocOwnerClassCode
//    );
//
//
//    @Query(     " SELECT doc"
//            +   " FROM ContentsCatalogMaster contents "
//            +           " INNER JOIN ContentsDetailMaster detail "
//            +                   " ON contents.id = detail.contentsCatalogMaster.id"
//            +           " INNER JOIN AttachDocMaster doc "
//            +                   " ON detail.id = doc.attachDocOwnerId"
//            +   " WHERE contents.id = :contentsCatalogId "
//            +           " AND detail.detailItemTypeCode in (:#{#detailItemTypeCodes}) "
//            +           " AND doc.attachDocOwnerClassCode in (:#{#attachDocOwnerClassCode}) "
//    )
//    List<AttachDocMaster> findByCatalogIdAndDetailItemTypeCodesAndAttachDocOwnerClassCodes(
//            @Param("contentsCatalogId") String contentsCatalogId,
//            @Param("detailItemTypeCodes") List<DetailItemTypeCode> detailItemTypeCodes,
//            @Param("attachDocOwnerClassCode") List<AttachDocOwnerClassCode> attachDocOwnerClassCode
//    );
//
//    Optional<AttachDocMaster> findTop1ByAttachDocOwnerIdOrderByCreatedDateDesc(String attachDocOwnerId);
//
//
//    @Query(     " SELECT doc"
//            +   " FROM ContentsCatalogMaster contents "
//            +           " INNER JOIN ContentsProvidersInfo provider "
//            +                   " ON contents.id = provider.contentsCatalogMaster.id AND provider.applyYn = true"
//            +           " INNER JOIN AffiliateIntroduce affiliate "
//            +                   " ON provider.companyMaster.id = affiliate.companyMaster.id"
//            +           " INNER JOIN AttachDocMaster doc "
//            +                   " ON affiliate.id = doc.attachDocOwnerId"
//            +   " WHERE contents.id = :contentsCatalogId "
//            +       " AND affiliate.introduceTypeCode = :#{#introduceTypeCode} "
//            +       " AND doc.attachDocOwnerClassCode  = 'AFFILIATE_INTRODUCE' "
//    )
//    List<AttachDocMaster> findByCatalogIdForProviders(
//            @Param("contentsCatalogId") String contentsCatalogId,
//            @Param("introduceTypeCode") IntroduceTypeCode introduceTypeCode
//    );

    Long deleteByAttachDocOwnerId(String attachDocOwnerId);

    List<AttachDocMaster> findAllByIdInOrderByCreatedDateAsc(List<String> attachDocIds);

}

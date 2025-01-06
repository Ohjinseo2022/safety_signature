package com.safety_signature.safety_signature_back.app.regional_address.domain;

import com.safety_signature.safety_signature_back.app.common.domain.AbstractAuditingEntity;
import com.safety_signature.safety_signature_back.app.company.domain.CompanyMaster;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Comment;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
@Data
@Setter
@Getter
@ToString
@EqualsAndHashCode(callSuper = true,of="id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name="tb_regional_address")
@EntityListeners(AuditingEntityListener.class)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RegionalAddress  extends AbstractAuditingEntity<String> implements Serializable {
    private static final long serialVersionUID = 1L;
    @Comment("근무지역정보 고유 ID")
    @NotNull
    @Size(max = 36)
    @Id
    @Column(name="id", length = 36, nullable = false,unique = true)
    private String id;

    @Comment("우편 번호")
    @Size(max =100)
    @Column(name = "zone_code",length = 100)
    private String zoneCode;

    @Comment("도로명주소")
    @Size(max =5000)
    @Column(name = "road_address",length = 5000)
    private String roadAddress;

    @Comment("지번주소")
    @Size(max =5000)
    @Column(name = "jibun_address",length = 5000)
    private String jibunAddress;

    @Comment("상세주소")
    @Size(max =5000)
    @Column(name = "detail_address",length = 5000)
    private String detailAddress;

    @Comment("위도")
    @Size(max =1000)
    @Column(name = "latitude",length = 1000)
    private String latitude;

    @Comment("경도")
    @Size(max =1000)
    @Column(name = "longitude",length = 1000)
    private String longitude;

    @Comment("회사 정보 고유 ID")
    @Size(max = 36)
    @NotNull
    @Column(name="company_master_id",length = 36 ,updatable = false)
    private String companyMasterId;

    @Comment("회사 정보 고유 ID")
    @Size(max=36)
    @ManyToOne
    @NotNull
    @JoinColumn(name="company_master_id",insertable = false,updatable = false,foreignKey = @ForeignKey(name="fk_regionaladdress_companymaster"))
    private CompanyMaster companyMaster;

}

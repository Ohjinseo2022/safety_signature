package com.safety_signature.safety_signature_back.app.company.domain;


import com.safety_signature.safety_signature_back.app.common.domain.AbstractAuditingEntity;
import com.safety_signature.safety_signature_back.app.user.domain.UserMaster;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.ManyToAny;
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
@Table(name="tb_company_member")
@EntityListeners(AuditingEntityListener.class)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CompanyMember extends AbstractAuditingEntity<String> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Comment("회사 구성원 고유 ID")
    @NotNull
    @Size(max = 100)
    @Id
    @Column(name="id", length = 100, nullable = false,unique = true)
    private String id;

    @Comment("공사 종목")
    @NotNull
    @Size(max =100)
    @Column(name = "construction business",length = 100)
    private String constructionBusiness;

    @Comment("활성 상태")
    @NotNull
    @Size(max =100)
    @Column(name = "status_code",length = 100)
    private String statusCode;

    @Comment("회원 관리 고유 ID")
    @Size(max = 36)
    @NotNull
    @Column(name="user_master_id",length = 36 ,updatable = false)
    private String userMasterId;

    @Comment("회원 고유 ID")
    @ManyToOne
    @NotNull
    @JoinColumn(name = "user_master_id" , insertable = false, updatable = false,foreignKey = @ForeignKey(name = "fk_companymember_usermaster"))
    private UserMaster userMaster;


    @Comment("회사 정보 고유 ID")
    @Size(max = 36)
    @NotNull
    @Column(name="company_master_id",length = 36 ,updatable = false)
    private String companyMasterId;

    @Comment("회사 정보 고유ID")
    @Size(max=36)
    @ManyToOne
    @NotNull
    @JoinColumn(name="company_master_id",insertable = false,updatable = false,foreignKey = @ForeignKey(name="fk_companymember_companymaster"))
    private CompanyMaster companyMaster;
}

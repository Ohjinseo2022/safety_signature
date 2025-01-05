package com.safety_signature.safety_signature_back.app.company.domain;


import com.safety_signature.safety_signature_back.app.common.domain.AbstractAuditingEntity;
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
@Table(name="tb_company_master")
@EntityListeners(AuditingEntityListener.class)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CompanyMaster  extends AbstractAuditingEntity<String> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Comment("회사 고유 ID")
    @NotNull
    @Size(max = 36)
    @Id
    @Column(name="id", length = 36, nullable = false,unique = true)
    private String id;

    @Comment("회사명")
    @NotNull
    @Size(max = 1000)
    @Column(name = "company_name",length = 1000,nullable = false)
    private String companyName;

    @Comment("사업자 번호")
    @Size(max = 1000)
    @Column(name = "business_number",length = 1000)
    private String businessNumber;

    @Comment("사업자 대표")
    @Size(max=100)
    @Column(name="business_representative",length = 100)
    private String businessRepresentative;

    @Comment("담당자 이름")
    @Size(max=100)
    @Column(name="agent_name",length = 100)
    private String agentName;


    @Comment("담당자 연락처")
    @Size(max=1000)
    @Column(name="agent_phone_number",length = 1000)
    private String agentPhoneNumber;


    @Comment("회사상태코드")
    @Size(max=100)
    @Column(name="company_status_code",length = 100)
    private String companyStatusCode;

    @Comment("회사유형")
    @Size(max=100)
    @Column(name="company_type",length = 100)
    private String companyType;

}

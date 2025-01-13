package com.safety_signature.safety_signature_back.app.common.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.safety_signature.safety_signature_back.app.common.enumeration.OperationTypeCode;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Comment;

import java.io.Serializable;

/**
 * 첨부문서이력
 */
@Getter
@Setter
@ToString @EqualsAndHashCode(callSuper = false, of="id")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_attach_doc_hist")
@Comment("첨부 문서 이력 테이블")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AttachDocHist extends AbstractAuditingEntity<String> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 첨부문서이력ID
     */
    @Comment("첨부문서이력ID")
    @NotNull
    @Size(max = 36)
    @Id
    @Column(name = "id", length = 36, nullable = false, unique = true)
    private String id;

    /**
     * 작업구분코드
     */
    @Comment("작업구분코드")
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "operation_type_code", length = 30, nullable = false)
    private OperationTypeCode operationTypeCode;

    /**
     * 작업자IP주소
     */
    @Comment("작업자IP주소")
    @Size(max = 20)
    @Column(name = "operator_ip_address", length = 20)
    private String operatorIpAddress;

    /**
     * 작업목적설명
     */
    @Comment("작업목적설명")
    @Size(max = 4000)
    @Column(name = "operation_goal_explain", length = 4000)
    private String operationGoalExplain;

    @Comment("첨부문서UUID")
    @ManyToOne @JoinColumn(name = "attach_doc_uuid", foreignKey = @ForeignKey(name="fk_attachDocHist_attachDocMaster"))
    @JsonIgnoreProperties(
            value = {
                    "attachDocMaster",
            },
            allowSetters = true
    )
    private AttachDocMaster attachDocMaster;

    /**
     * 첨부파일id
     */
    @Comment("첨부파일id")
    @Size(max = 40)
    @Column(name = "attach_doc_uuid", length = 20, nullable = false, insertable = false, updatable = false)
//    @Column(name = "user_busi_type_id", length = 50, insertable = false, updatable = false)
    private String attachDocUuid;

}

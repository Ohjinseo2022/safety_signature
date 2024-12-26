package com.safety_signature.safety_signature_back.app.common.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Comment;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 첨부문서분할내역
 */
@Getter
@Setter
@ToString @EqualsAndHashCode(callSuper = false, of="id")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_attach_doc_partition")
@Comment("첨부 문서 분할 내역 테이블")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AttachDocPartition extends AbstractAuditingEntity<String> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 첨부문서분할내역ID
     */
    @Comment("첨부문서분할내역ID")
    @NotNull
    @Size(max = 36)
    @Id
    @Column(name = "id", length = 36, nullable = false, unique = true)
    private String id;

    /**
     * 페이지순번
     */
    @Comment("페이지순번")
    @Column(name = "page_turn")
    private Long pageTurn;

    /**
     * 첨부문서ID
     */
    @Comment("첨부문서ID")
    @Size(max = 100)
    @Column(name = "attach_doc_id", length = 100)
    private String attachDocId;

    /**
     * 첨부문서위치
     */
    @Comment("첨부문서위치")
    @Size(max = 1000)
    @Column(name = "attach_doc_position", length = 1000)
    private String attachDocPosition;

    /**
     * 첨부문서크기
     */
    @Comment("첨부문서크기")
    @Column(name = "attach_doc_size", precision = 18, scale = 5)
    private BigDecimal attachDocSize;

    @Comment("첨부문서UUID")
    @ManyToOne
    @JoinColumn(name = "attach_doc_uuid", foreignKey = @ForeignKey(name="fk_attachdocpartition_attachdocmaster"))
    @JsonIgnoreProperties(
            value = {
                    "attachDocMaster",
            },
            allowSetters = true
    )
    private AttachDocMaster attachDocMaster;

}

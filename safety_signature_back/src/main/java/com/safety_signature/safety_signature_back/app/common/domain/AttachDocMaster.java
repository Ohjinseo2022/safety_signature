package com.safety_signature.safety_signature_back.app.common.domain;


import com.safety_signature.safety_signature_back.app.common.enumeration.AttachDocOwnerClassCode;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Comment;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Setter
@Getter
@ToString
@EqualsAndHashCode(callSuper = true,of="id")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="tb_attach_doc_master")
@Comment("첨부 문서 관리 테이블")
@EntityListeners(AuditingEntityListener.class)
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AttachDocMaster extends AbstractAuditingEntity<String> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Comment("첨부 문서 관리 고유 ID")
    @Size(max=36)
    @NotNull
    @Id
    @Column(name = "id", length = 36,nullable = false ,unique = true)
    private String id;

    @Comment("첨부문서명")
    @Size(max = 600)
    @Column(name = "attach_doc_name", length = 600)
    private String attachDocName;

    /**
     * 첨부문서설명
     */
    @Comment("첨부문서설명")
    @Size(max = 4000)
    @Column(name = "attach_doc_explain", length = 4000)
    private String attachDocExplain;

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
     * 첨부문서소유자유형코드
     */
    @Comment("첨부문서소유자유형코드")
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "attach_doc_owner_class_code", length = 30, nullable = false)
    private AttachDocOwnerClassCode attachDocOwnerClassCode;

    /**
     * 첨부문서소유자ID
     */
    @Comment("첨부문서소유자ID")
    @NotNull
    @Size(max = 36)
    @Column(name = "attach_doc_owner_id", length = 36, nullable = false)
    private String attachDocOwnerId;

    /**
     * 첨부문서크기
     */
    @Comment("첨부문서크기")
    @Column(name = "attach_doc_size", precision = 18, scale = 5)
    private BigDecimal attachDocSize;

    /**
     * 첨부문서다운로드제한일시
     */
    @Comment("첨부문서다운로드제한일시")
    @Column(name = "attach_download_limit_dtime")
    private LocalDateTime attachDownloadLimitDtime;

    /**
     * 첨부문서보관제한일시
     */
    @Comment("첨부문서보관제한일시")
    @Column(name = "attach_storage_limit_dtime")
    private LocalDateTime attachStorageLimitDtime;

    /**
     * MinIO삭제여부
     */
    @Comment("MinIO삭제여부")
    @Column(name = "minio_delete_yn")
    private Boolean minioDeleteYn;

}

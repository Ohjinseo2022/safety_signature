package com.safety_signature.safety_signature_back.app.common.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Comment;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Setter
@Getter
@ToString
@EqualsAndHashCode(callSuper = true,of="id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name="tb_attach_doc_master")
@Comment("첨부 문서 관리 테이블")
@EntityListeners(AuditingEntityListener.class)
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AttachDocMaster extends AbstractAuditingEntity<String> implements Serializable {

    @Comment("첨부 문서 관리 고유 ID")
    @Size(max=36)
    @NotNull
    @Id
    @Column(name = "id",nullable = false ,unique = true)
    private String id;

    @Comment("첨부 문서 소유 ID")
    @Size(max=36)
    @NotNull
    @Column(name = "owner_id",length = 36,nullable = false)
    private String ownerId;

    @Comment("첨부문서 이름")
    @Size(max=1000)
    @NotNull
    @Column(name = "attach_name",length = 1000,nullable = false)
    private String attachName;

    @Comment("첨부문서 확장자")
    @Size(max=20)
    @NotNull
    @Column(name = "attach_file_type",length = 20,nullable = false)
    private String attachFileType;


    @Comment("minio 경로정보")
    @Size(max=5000)
    @NotNull
    @Column(name = "attach_minio_path",length = 5000,nullable = false)
    private String attachMinioPath;

    @Comment("다운로드 가능 여부")
    @Column(name = "attach_download_yn")
    private Boolean attachDownloadYn = true; // 별도로 지정하지 않으면 true 상태

    @Comment("첨부 문서 용량 KB 단위")
    @NotNull
    @Column(name = "attach_size",nullable = false)
    private BigDecimal attachSize;

}

package com.safety_signature.safety_signature_back.app.approve_master.domain;

import com.safety_signature.safety_signature_back.app.common.domain.AbstractAuditingEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Comment;
import org.jetbrains.annotations.NotNull;
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
@Table(name="tb_approve_master")
@EntityListeners(AuditingEntityListener.class)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ApproveMaster extends AbstractAuditingEntity<String> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Comment("전자 결제 명단 고유 ID")
    @NotNull
    @Size(max=36)
    @Id
    @Column(name = "id",length=36 , nullable=false,unique=true)
    private String id;

    @Comment("게시글 고유 ID")
    @NotNull
    @Size(max = 36)
    @Column(name = "bulletin_board_id",length = 36,nullable = false)
    private String bulletinBoardId;

    @Comment("회원 고유 ID")
    @NotNull
    @Size(max = 36)
    @Column(name = "user_master_id",length = 36,nullable = false)
    private String userMasterId;

    @Comment("회원 이름")
    @NotNull
    @Size(max = 100)
    @Column(name = "user_name",length = 100,nullable = false)
    private String userName;

    @Comment("첨부문서 ID")
    @NotNull
    @Size(max = 36)
    @Column(name = "attach_doc_id",length = 36,nullable = false)
    private String attachDocId;

    /**
     * TODO : 추후 eum 으로 변경하여 상태코드 관리
     */
    @Comment("결제 상태 코드")
    @NotNull
    @Size(max = 100)
    @Column(name = "approveStatus",length = 100)
    private String approveStatus;

    @Comment("업체명")
    @Size(max = 1000)
    @Column(name = "company_name")
    private String companyName;


    @Comment("공사 종목명")
    @Size(max = 1000)
    @Column(name = "construction_business")
    private String constructionBusiness;

}

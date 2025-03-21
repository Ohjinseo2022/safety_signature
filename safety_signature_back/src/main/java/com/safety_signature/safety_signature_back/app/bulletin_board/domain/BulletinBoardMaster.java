package com.safety_signature.safety_signature_back.app.bulletin_board.domain;

import com.safety_signature.safety_signature_back.app.common.domain.AbstractAuditingEntity;
import com.safety_signature.safety_signature_back.app.common.enumeration.SafetySignatureStatusCode;
import com.safety_signature.safety_signature_back.app.user.domain.UserMaster;
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
@Table(name="tb_bulletin_board_master")
@EntityListeners(AuditingEntityListener.class)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BulletinBoardMaster extends AbstractAuditingEntity<String> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Comment("게시판 고유 ID")
    @NotNull
    @Size(max = 36)
    @Id
    @Column(name="id", length = 36, nullable = false,unique = true)
    private String id;

    @Comment("게시글 제목")
    @NotNull
    @Size(max = 1000)
    @Column(name = "board_title",length = 1000,nullable = false)
    private String boardTitle;

    @Comment("게시글 내용")
    @Size(max = 5000)
    @Column(name = "board_contents",length = 5000)
    private String boardContents;

    @Comment("첨부파일 유무")
    @Column(name = "attach_yn")
    private Boolean attachYn;

    @Comment("게시글 오너 ID")
    @Size(max = 36)
    @Column(name="user_master_id",length = 36 ,updatable = false)
    private String userMasterId;

    @Comment("회원 고유 ID")
    @ManyToOne
    @NotNull
    @JoinColumn(name = "user_master_id" , insertable = false, updatable = false,foreignKey = @ForeignKey(name = "fk_tokenmanagementmaster_usermaster"))
    private UserMaster userMaster;

    @Comment("현장 주소 정보")
    @Size(max = 5000)
    @Column(name="site_address")
    private String siteAddress;

    @Comment("현장명")
    @Size(max = 5000)
    @Column(name="site_name")
    private String siteName;

    @Enumerated(value = EnumType.STRING)
    @Comment("게시글 타입 및 상태코드")
    @Column(name="board_status_code")
    private SafetySignatureStatusCode boardStatusCode;

    @Comment("공개 기업 정보")
    @Column(name="allowed_companies")
    private String allowedCompanies;

    @Comment("결제완료 유무")
    @Column(name = "completion_yn")
    private Boolean completionYn = false;
}

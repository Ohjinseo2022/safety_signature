package com.safety_signature.safety_signature_back.app.bulletin_board.domain;


import com.safety_signature.safety_signature_back.app.common.domain.AbstractAuditingEntity;
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
@Table(name="tb_bulletin_board_attach_info")
@EntityListeners(AuditingEntityListener.class)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BulletinBoardAttachInfo extends AbstractAuditingEntity<String> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Comment("게시판 첨부문서 고유 ID")
    @NotNull
    @Size(max = 36)
    @Id
    @Column(name="id", length = 36, nullable = false,unique = true)
    private String id;

    @Comment("첨부 문서 ID")
    @Size(max = 36)
    @Column(name="attach_doc_id",length = 36 ,updatable = false)
    private String attach_doc_id;


    @Comment("게시글 오너 ID")
    @Size(max = 36)
    @Column(name="user_master_id",length = 36 ,updatable = false)
    private String userMasterId;

    @Comment("회원 고유 ID")
    @ManyToOne
    @NotNull
    @JoinColumn(name = "user_master_id" , insertable = false, updatable = false,foreignKey = @ForeignKey(name = "fk_tokenmanagementmaster_usermaster"))
    private UserMaster userMaster;
}

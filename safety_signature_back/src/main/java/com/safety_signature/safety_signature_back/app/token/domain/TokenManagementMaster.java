package com.safety_signature.safety_signature_back.app.token.domain;


import com.safety_signature.safety_signature_back.app.common.domain.AbstractAuditingEntity;
import com.safety_signature.safety_signature_back.app.user.domain.UserMaster;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
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
@Table(name="tb_token_management_master")
@EntityListeners(AuditingEntityListener.class)
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//@SuppressWarnings()
public class TokenManagementMaster extends AbstractAuditingEntity<String> implements Serializable {
    @Comment("토큰 관리ID")
    @NotNull
    @Size(max = 36)
    @Id
    @Column(name="id", length = 36, nullable = false,unique = true)
    private String id;

    @Comment("회원 관리 고유 ID")
    @Size(max = 36)
    @Column(name="user_master_id",length = 36 ,updatable = false)
    private String userMasterId;

    @Comment("회원 고유 ID")
    @ManyToOne
    @NotNull
    @JoinColumn(name = "user_master_id" , insertable = false, updatable = false,foreignKey = @ForeignKey(name = "fk_tokenmanagementmaster_usermaster"))
    private UserMaster userMaster;

    @Comment("엑세스 토큰")
    @Size(max = 5000)
    @Column(name = "access_token", length = 5000,nullable = false)
    private String accessToken;

    @Comment("리프레시토큰")
    @Size(max=5000)
    @Column(name="refresh_token",length = 5000,nullable = false)
    private String refreshToken;
}

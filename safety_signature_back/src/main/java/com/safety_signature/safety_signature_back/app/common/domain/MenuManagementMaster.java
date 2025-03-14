package com.safety_signature.safety_signature_back.app.common.domain;

import com.safety_signature.safety_signature_back.app.common.enumeration.UserTypeCode;
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
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="tb_menu_management_master")
@Comment("메뉴 관리 테이블")
@EntityListeners(AuditingEntityListener.class)
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MenuManagementMaster  extends AbstractAuditingEntity<String> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Comment("메뉴 관리 테이블 고유 ID")
    @Size(max=36)
    @NotNull
    @Id
    @Column(name = "id", length = 36,nullable = false ,unique = true)
    private String id;

    @Comment("메뉴이름")
    @Size(max=1000)
    @Column(name="menu_name",length = 1000)
    private String menuName;


    @Comment("메뉴경로")
    @Size(max=5000)
    @Column(name="menu_path",length = 5000)
    private String menuPath;

    @Comment("상위메뉴 ID")
    @Size(max=36)
    @Column(name="parent_id",length = 36)
    private String parentId;

    @Comment("메뉴 접근 권한")
    @Size(max=1000)
    @Enumerated(EnumType.STRING)
    @Column(name="menu_auth_code",length = 1000)
    private UserTypeCode menuAuthCode;

    @Comment("메뉴사용 유무")
    @Column(name="is_menu_active")
    private boolean isMenuActive = true;
}

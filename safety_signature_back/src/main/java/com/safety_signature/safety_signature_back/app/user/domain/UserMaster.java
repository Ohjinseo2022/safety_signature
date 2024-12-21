package com.safety_signature.safety_signature_back.app.user.domain;


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
@ToString @EqualsAndHashCode(callSuper = true,of="id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name="tb_user_master")
@EntityListeners(AuditingEntityListener.class)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//@SuppressWarnings()
public class UserMaster extends AbstractAuditingEntity<String> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Comment("회원 고유 ID")
    @NotNull
    @Size(max = 36)
    @Id
    @Column(name="id", length = 36, nullable = false,unique = true)
    private String id;

    @Comment("회원 이름")
    @NotNull
    @Size(max = 100)
    @Column(name = "user_name",length = 100,nullable = false)
    private String userName;

    /**
     * 핸드폰 번호 추후 암호화하여 수집예정
     */
    @Comment("핸드폰 번호")
    @NotNull
    @Size(max = 2000)
    @Column(name = "mobile",length = 2000)
    private String mobile;

    /**
     * 비밀번호의 경우 소셜로그인처리시 별도로 입력받지 않음
     */
    @Comment("비밀번호")
    @Size(max = 200)
    @Column(name="user_password",length = 200)
    private String userPassword;

    /**
     * 소셜로그인 진행시 필수적으로 필요함
     */
    @Comment("회원 이메일")
    @NotNull
    @Size(max = 100)
    @Column(name = "email",length = 100,nullable = false)
    private String email;

    /**
     * 소셜 로그인시 제공받은 회원 프로필 이미지 URL
     */
    @Comment("회원 프로필 이미지 URL")
    @Size(max = 5000)
    @Column(name= "profile_image_uri",length = 5000)
    private String profileImageUri;
    /**
     * 로그인 플랫폼 종류
     */
    @Comment("구글연동유무")
    @Column(name="google_sign_in")
    private boolean googleSignIn;

    @Comment("카카오연동유무")
    @Column(name="kakao_sign_in")
    private boolean kakaoSignIn;

    @Comment("네이버연동유무")
    @Column(name="naver_sign_in")
    private boolean naverSignIn;

}

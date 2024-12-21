package com.safety_signature.safety_signature_back.app.common.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.Instant;
/**
*   모든 테이블에 공통으로 적용되는 컬럼 공통으로 생성
*
 * */
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"version", "createdBy", "createdDate", "lastModifiedBy", "lastModifiedDate"}, allowGetters = true)
public abstract class AbstractAuditingEntity<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Version
    @Column(name="version")
    @Comment("버전 정보")
    private Long version = this.getVersion() == null ? 1 :  this.getVersion() + 1;

    @CreatedBy
    @Column(name="created_by",nullable = false,length = 50,updatable = false)
    @Comment("최초생성자")
    private String createdBy;

    @CreatedDate
    @Column(name="created_date",updatable = false)
    @Comment("최초 생성 일시")
    private Instant createdDate = Instant.now();//디폴트 값지정

    @LastModifiedBy
    @Column(name ="last_modified_by",length = 50)
    @Comment("마지막 변경자")
    private String lastModifiedBy;

    @LastModifiedDate
    @Column(name="last_modified_date")
    @Comment("마지막 변경 일시")
    private Instant lastModifiedDate = Instant.now();

    public abstract T getId();




}

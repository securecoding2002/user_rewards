package com.example.assignment.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
    value = {"createdAt", "updatedAt"},
    allowGetters = true)
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@MappedSuperclass
public abstract class Auditable {
  @CreatedDate
  @Column(nullable = false, updatable = false, columnDefinition = "DATETIME(6)")
  @CreationTimestamp
  private LocalDate createdAt;

  @LastModifiedDate
  @Column(nullable = false, columnDefinition = "DATETIME(6)")
  @CreationTimestamp
  private LocalDate updatedAt;

  @Version private Long version; // used for optimistic locking
}

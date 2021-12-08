package com.epam.esm.dao.audit;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import java.time.Instant;

public class AuditableListener {
  @PrePersist
  void preCreate(Auditable auditable) {
    System.out.println(auditable + ", persist at " + Instant.now());
  }

  @PreUpdate
  void preUpdate(Auditable auditable) {
    System.out.println(auditable + ", update at " + Instant.now());
  }

  @PreRemove
  void preRemove(Auditable auditable) {
    System.out.println(auditable + ", remove at " + Instant.now());
  }
}

package com.epam.esm.dao.audit;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.sql.Timestamp;
import java.time.Instant;

public class AuditListener {
  @PrePersist
  public void prePersist(Auditable auditable) {
      Timestamp now = Timestamp.from(Instant.now());
      auditable.setCreatedDate(now);
      auditable.setModifiedDate(now);
  }

  @PreUpdate
  public void preUpdate(Auditable auditable) {
    auditable.setModifiedDate(Timestamp.from(Instant.now()));
  }
}

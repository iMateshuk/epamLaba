package com.epam.esm.dao.audit;

import java.sql.Timestamp;

public interface Auditable {
  Timestamp getCreatedDate();

  void setCreatedDate(Timestamp createdDate);

  Timestamp getModifiedDate();

  void setModifiedDate(Timestamp modifiedDate);
}

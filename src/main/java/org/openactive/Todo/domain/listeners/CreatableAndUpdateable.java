package org.openactive.Todo.domain.listeners;

import java.util.Date;

public interface CreatableAndUpdateable
{
  Date getCreated();

  void setCreated(Date created);

  Date getLastUpdated();

  void setLastUpdated(Date lastUpdated);
}

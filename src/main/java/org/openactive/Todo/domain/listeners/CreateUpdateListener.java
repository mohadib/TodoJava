package org.openactive.Todo.domain.listeners;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

public class CreateUpdateListener
{
  @PrePersist
  @PreUpdate
  public void handle( CreatableAndUpdateable entity )
  {
    Date now = new Date();
    if( entity.getCreated() == null )
    {
      entity.setCreated(now);
    }
    entity.setLastUpdated(now);
  }
}

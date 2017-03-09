package org.openactive.Todo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.openactive.Todo.domain.listeners.CreatableAndUpdateable;
import org.openactive.Todo.domain.listeners.CreateUpdateListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table( name = "todo" )
@EntityListeners( {CreateUpdateListener.class} )
public class Todo implements CreatableAndUpdateable
{
  @Id
  @GeneratedValue( strategy = GenerationType.IDENTITY )
  @Column( nullable = false, unique = true )
  private Integer id;

  @Temporal( TemporalType.TIMESTAMP )
  @Column( name = "created", nullable = false )
  private Date created;

  @Temporal( TemporalType.TIMESTAMP )
  @Column( name = "lastUpdated" )
  private Date lastUpdated;

  @ManyToOne
  private User user;

  @Basic(optional = false)
  private String title;

  @Column( name="description", length = 5000)
  private String description;

  @Basic
  private boolean done;

  public Integer getId()
  {
    return id;
  }

  public void setId( Integer id )
  {
    this.id = id;
  }

  public Date getCreated()
  {
    return created;
  }

  public void setCreated( Date created )
  {
    this.created = created;
  }

  public Date getLastUpdated()
  {
    return lastUpdated;
  }

  public void setLastUpdated( Date lastUpdated )
  {
    this.lastUpdated = lastUpdated;
  }

  @JsonIgnore
  public User getUser()
  {
    return user;
  }

  public void setUser( User user )
  {
    this.user = user;
  }

  public String getTitle()
  {
    return title;
  }

  public void setTitle( String title )
  {
    this.title = title;
  }

  public String getDescription()
  {
    return description;
  }

  public void setDescription( String description )
  {
    this.description = description;
  }

  public boolean isDone()
  {
    return done;
  }

  public void setDone( boolean done )
  {
    this.done = done;
  }
}

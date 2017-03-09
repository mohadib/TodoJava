package org.openactive.Todo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.openactive.Todo.domain.listeners.CreatableAndUpdateable;
import org.openactive.Todo.domain.listeners.CreateUpdateListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@Table( name = "user" )
@EntityListeners( {CreateUpdateListener.class} )
public class User implements CreatableAndUpdateable
{
  @Id
  @GeneratedValue( strategy = GenerationType.IDENTITY )
  @Column( nullable = false, unique = true )
  private Integer id;

  @Column( nullable = false, unique = true )
  private String email;

  @Column( nullable = false, length = 60 )
  private String password;

  @Basic
  private String fname;

  @Basic
  private String lname;

  @Temporal( TemporalType.TIMESTAMP )
  @Column( name = "created", nullable = false )
  private Date created;

  @Temporal( TemporalType.TIMESTAMP )
  @Column( name = "lastUpdated" )
  private Date lastUpdated;

  @ManyToMany( fetch = FetchType.EAGER )
  private List< Role > roles;

  public Integer getId()
  {
    return id;
  }

  public void setId( Integer id )
  {
    this.id = id;
  }

  public String getEmail()
  {
    return email;
  }

  public void setEmail( String email )
  {
    this.email = email;
  }

  @JsonIgnore
  public String getPassword()
  {
    return password;
  }

  @JsonProperty
  public void setPassword( String password )
  {
    this.password = password;
  }

  public String getFname()
  {
    return fname;
  }

  public void setFname( String fname )
  {
    this.fname = fname;
  }

  public String getLname()
  {
    return lname;
  }

  public void setLname( String lname )
  {
    this.lname = lname;
  }

  @Override
  public Date getCreated()
  {
    return created;
  }

  @Override
  public void setCreated( Date created )
  {
    this.created = created;
  }

  @Override
  public Date getLastUpdated()
  {
    return lastUpdated;
  }

  @Override
  public void setLastUpdated( Date lastUpdated )
  {
    this.lastUpdated = lastUpdated;
  }

  public List< Role > getRoles()
  {
    return roles;
  }

  public void setRoles( List< Role > roles )
  {
    this.roles = roles;
  }

  @Override
  public boolean equals( Object o )
  {
    if ( this == o ) return true;
    if ( !( o instanceof User ) ) return false;

    User user = (User) o;

    return getEmail() != null ? getEmail().equals( user.getEmail() ) : user.getEmail() == null;
  }

  @Override
  public int hashCode()
  {
    return getEmail() != null ? getEmail().hashCode() : 0;
  }
}

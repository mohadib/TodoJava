package org.openactive.Todo.domain;

import javax.persistence.*;

@Entity
@Table(name = "role")
public class Role
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false, unique = true)
  private Integer id;

  @Column(nullable = false, unique = true)
  private String name;

  public static final transient Role ADMIN_ROLE = new Role("ROLE_ADMIN");
  public static final transient Role USER_ROLE = new Role("ROLE_USER");

  public Role(){}

  public Role(String name)
  {
    this.name = name;
  }

  public String getName()
  {
    return name;
  }

  @Override
  public boolean equals(Object o)
  {
    if (this == o) return true;
    if (!(o instanceof Role)) return false;

    Role role = (Role) o;

    return getName().equals(role.getName());
  }

  @Override
  public int hashCode()
  {
    return getName().hashCode();
  }
}

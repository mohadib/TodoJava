package org.openactive.Todo.dao;

import org.openactive.Todo.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository< Role, Integer >
{
  Role findOneByName( String name );
}

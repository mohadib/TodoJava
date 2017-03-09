package org.openactive.Todo.dao;

import org.openactive.Todo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository< User, Integer >
{
  User findOneByEmail( String email );
}

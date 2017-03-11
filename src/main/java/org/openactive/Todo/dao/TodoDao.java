package org.openactive.Todo.dao;

import org.openactive.Todo.domain.Todo;
import org.openactive.Todo.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TodoDao extends JpaRepository<Todo, Integer>
{
   @Query("SELECT x from Todo x where x.user = :user and ( x.title like %:title% or x.description like %:description% )")
   List<Todo> search(
     @Param( "user" ) User user,
     @Param( "title" ) String title,
     @Param( "description" ) String description,
     Pageable pageable
   );

   @Query("SELECT count(x.id) from Todo x where x.user = :user and ( x.title like %:title% or x.description like %:description% )")
   long searchCount(
     @Param( "user" ) User user,
     @Param( "title" ) String title,
     @Param( "description" ) String description
   );

   @Transactional
   @Modifying
   Integer deleteByUserAndId( User user, Integer id );

   List<Todo> findAllByUser( User user, Pageable pageable );

   Todo findOneByIdAndUser( Integer id, User user );

   long countByUser( User user );
}

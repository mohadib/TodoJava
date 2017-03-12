package org.openactive.Todo.bootstrap;

import org.openactive.Todo.dao.RoleDao;
import org.openactive.Todo.dao.TodoDao;
import org.openactive.Todo.dao.UserDao;
import org.openactive.Todo.domain.Role;
import org.openactive.Todo.domain.Todo;
import org.openactive.Todo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class AppBootstrap extends LoadData
{
   @Autowired
   private RoleDao roleDao;

   @Autowired
   private UserDao userDao;

   @Autowired
   private TodoDao todoDao;

   @Autowired
   private PasswordEncoder passwordEncoder;

   @Override
   public void load()
   {
      Role adminRole = new Role( "ROLE_ADMIN" );
      adminRole = roleDao.save( adminRole );

      Role userRole = new Role( "ROLE_USER" );
      userRole = roleDao.save( userRole );

      User jason = new User();
      jason.setFname( "Jason" );
      jason.setLname( "Davis" );
      jason.setEmail( "jdavis@openactive.org" );
      jason.setPassword( passwordEncoder.encode( "letmein" ) );
      jason.setRoles( Arrays.asList( adminRole, userRole ) );
      jason = userDao.save( jason );


      for ( int i = 0; i < 2; i++ )
      {
         Todo todo = new Todo();
         todo.setTitle( i + "Fix search with sort" );
         todo.setDescription( i + "Fix 'done'" );
         todo.setUser( jason );
         todoDao.save( todo );
      }

      User foo = new User();
      foo.setFname( "Foo" );
      foo.setLname( "McBar" );
      foo.setEmail( "foo@openactive.org" );
      foo.setPassword( passwordEncoder.encode( "letmein" ) );
      foo.setRoles( Arrays.asList( userRole ) );
      foo = userDao.save( foo );

      for ( int i = 0; i < 30; i++ )
      {
         Todo todo = new Todo();
         todo.setTitle( i + " A Todo for Foo" );
         todo.setDescription( i + "Foo should create an app with React and Redux" );
         todo.setUser( foo );
         todoDao.save( todo );
      }

      //PageRequest pageable = new PageRequest( 0, 20, Sort.Direction.ASC, "id");
      //List<Todo> todos = todoDao.search( jason, "create", "create",  pageable);
   }
}

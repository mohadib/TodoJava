package org.openactive.Todo.controller;

import org.openactive.Todo.dao.RoleDao;
import org.openactive.Todo.dao.UserDao;
import org.openactive.Todo.domain.Role;
import org.openactive.Todo.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController
{
   private final Logger LOG = LoggerFactory.getLogger( getClass() );

   @Autowired
   private UserDao userDao;

   @Autowired
   private RoleDao roleDao;

   @Autowired
   private PasswordEncoder passwordEncoder;


   @GetMapping("/{id}")
   public ResponseEntity<User> get( User user, @PathVariable("id") Integer id )
   {
      if ( user.getRoles().contains( Role.ADMIN_ROLE ) )
      {
         return ResponseEntity.ok( userDao.findOne( id ) );
      }

      return ResponseEntity.ok( user );
   }

   @GetMapping
   public ResponseEntity get( User user )
   {
      return ResponseEntity.ok( user );
   }

   @PostMapping
   public ResponseEntity<User> create( User user, @RequestBody User newUser )
   {
      if ( !user.getRoles().contains( Role.ADMIN_ROLE ) )
      {
         throw new IllegalArgumentException();
      }

      List<Role> roles = newUser.getRoles().stream()
        .distinct()
        .map( role -> roleDao.findOneByName( role.getName() ) )
        .collect( Collectors.toList() );

      newUser.setRoles( roles );
      newUser.setPassword( passwordEncoder.encode( newUser.getPassword() ) );
      return ResponseEntity.ok( userDao.save( newUser ) );
   }

   /**
    * only used to updated a users passwd
    */
   @PutMapping
   public ResponseEntity<User> updatePasswd( User user, @RequestBody User updateUser )
   {
      if ( user.equals( updateUser ) )
      {
         LOG.info( "User {} is updating password.", user.getEmail() );
         user.setPassword( passwordEncoder.encode( updateUser.getPassword() ) );
         return ResponseEntity.ok( userDao.save( user ) );
      }
      else if ( user.getRoles().contains( Role.ADMIN_ROLE ) )
      {
         User existing = userDao.findOne( updateUser.getId() );
         existing.setPassword( passwordEncoder.encode( updateUser.getPassword() ) );
         return ResponseEntity.ok( userDao.save( existing ) );
      }
      else throw new IllegalArgumentException();
   }


   @PatchMapping
   public ResponseEntity<User> update( User user, @RequestBody User updateUser )
   {
      if ( user.getRoles().contains( Role.ADMIN_ROLE ) )
      {
         LOG.info( "An admin user {} is updating another user {}", user.getEmail(), updateUser.getEmail() );
         user.setEmail( updateUser.getEmail() );
         user.getRoles().clear();

         updateUser.getRoles().stream()
           .distinct()
           .forEach( role ->
           {
              Role fromDb = roleDao.findOneByName( role.getName() );
              user.getRoles().add( fromDb );
           } );
      }
      if ( user.equals( updateUser ) )
      {
         LOG.info( "User {} is updating their information", user.getEmail() );
      }
      else if ( user.getId().equals( updateUser.getId() ) )
      {
         LOG.info( "User {} is updating their email address to {}", user.getEmail(), updateUser.getEmail() );
         user.setEmail( updateUser.getEmail() );
      }
      else
      {
         LOG.error( "User {} is trying to update someone elses account: {}", user.getEmail(), updateUser.getEmail() );
         throw new IllegalArgumentException();
      }

      user.setFname( updateUser.getFname() );
      user.setLname( updateUser.getLname() );
      return ResponseEntity.ok( userDao.save( user ) );
   }
}

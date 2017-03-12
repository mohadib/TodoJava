package org.openactive.Todo.controller;

import org.apache.commons.lang3.StringUtils;
import org.openactive.Todo.dao.UserDao;
import org.openactive.Todo.domain.User;
import org.openactive.Todo.login.LoginRequest;
import org.openactive.Todo.login.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController
{
   @Autowired
   private AuthenticationManager authenticationManager;

   @Autowired
   private UserDao userDao;


   @GetMapping
   public ResponseEntity logout( User user )
   {
      return null;
   }

   @PostMapping
   public ResponseEntity<LoginResponse> login( @RequestBody LoginRequest request )
   {
      LoginResponse response = new LoginResponse( false, "fail" );

      Authentication existingAuth = SecurityContextHolder.getContext().getAuthentication();
      if ( existingAuth != null )
      {
         boolean isAnon = existingAuth.getAuthorities().stream()
           .filter( auth -> auth.getAuthority().equals( "ROLE_ANONYMOUS" ) )
           .findAny()
           .isPresent();

         if ( !isAnon )
         {
            response.setMessage( "Already logged in" );
            return ResponseEntity.badRequest().body( response );
         }

      }

      if ( StringUtils.isBlank( request.getUsername() ) )
      {
         response.setMessage( "Username can not be blank" );
      }
      else if ( StringUtils.isBlank( request.getPassword() ) )
      {
         response.setMessage( "Password can not be blank" );
      }
      else
      {
         Authentication authToken = new UsernamePasswordAuthenticationToken( request.getUsername(), request.getPassword() );
         try
         {
            Authentication auth = authenticationManager.authenticate( authToken );
            SecurityContextHolder.getContext().setAuthentication( auth );
            User user = userDao.findOneByEmail( request.getUsername() );
            response = new LoginResponse( true, "success", user );
         }
         catch ( AuthenticationException ae )
         {
            ae.printStackTrace();
            response.setMessage( ae.getMessage() );
         }
      }

      return ResponseEntity
        .status( response.isSuccess() ? HttpStatus.OK : HttpStatus.FORBIDDEN )
        .body( response );
   }
}

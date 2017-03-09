package org.openactive.Todo.login;

import org.openactive.Todo.dao.UserDao;
import org.openactive.Todo.domain.Role;
import org.openactive.Todo.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.stream.Collectors;

public class JpaUserService implements UserDetailsService
{
  final Logger LOG = LoggerFactory.getLogger( getClass() );

  @Autowired
  private UserDao userDao;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
  {
    User user = userDao.findOneByEmail( username );
    if( user == null )
    {
      LOG.error( "Could not find user: {}", username );
      throw new UsernameNotFoundException("Not found");
    }

    Collection<GrantedAuthority> auths = user.getRoles().stream()
        .map( Role::getName )
        .map( name -> new SimpleGrantedAuthority( name ) )
        .collect( Collectors.toList() );

    org.springframework.security.core.userdetails.User springUser =
      new org.springframework.security.core.userdetails.User( user.getEmail(), user.getPassword(), true, true, true, true, auths);

    LOG.info( "Logging in user {} with roles {}", username,  user.getRoles());

    return springUser;
  }
}

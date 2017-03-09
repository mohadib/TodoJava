package org.openactive.Todo.misc;

import org.openactive.Todo.dao.UserDao;
import org.openactive.Todo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class UserArgumentResolver implements HandlerMethodArgumentResolver
{
  @Autowired
  private UserDao userDao;

  @Override
  public boolean supportsParameter( MethodParameter methodParameter )
  {
    return methodParameter.getParameterType().equals( User.class );
  }

  @Override
  public Object resolveArgument
  (
    MethodParameter methodParameter,
    ModelAndViewContainer modelAndViewContainer,
    NativeWebRequest nativeWebRequest,
    WebDataBinderFactory webDataBinderFactory
  )
  throws Exception
  {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if ( principal == null ) return null;

    UserDetails details = (UserDetails) principal;
    return userDao.findOneByEmail( details.getUsername() );
  }
}

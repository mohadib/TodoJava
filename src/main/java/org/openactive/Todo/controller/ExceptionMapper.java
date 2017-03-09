package org.openactive.Todo.controller;

import org.openactive.Todo.misc.RestError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionMapper
{
  @ExceptionHandler({Exception.class})
  public ResponseEntity< RestError > handleErrors( HttpServletRequest req, Exception ex )
  {
    return new ResponseEntity<>( new RestError( 400, "Bad Request" ), HttpStatus.BAD_REQUEST );
  }
}

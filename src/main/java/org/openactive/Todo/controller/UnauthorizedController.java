package org.openactive.Todo.controller;

import org.openactive.Todo.misc.RestError;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/errors")
public class UnauthorizedController
{
   @ResponseStatus(HttpStatus.FORBIDDEN)
   @RequestMapping("/unauthorized")
   @ResponseBody
   public RestError http403()
   {
      return new RestError( 403, "Access Denied" );
   }
}

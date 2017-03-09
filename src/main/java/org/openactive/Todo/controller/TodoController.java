package org.openactive.Todo.controller;

import org.openactive.Todo.dao.TodoDao;
import org.openactive.Todo.domain.Todo;
import org.openactive.Todo.domain.User;
import org.openactive.Todo.misc.PageableResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController
{
   @Autowired
   private TodoDao todoDao;

   @PostMapping
   public ResponseEntity<Todo> post( User user, @RequestBody Todo todo )
   {
      todo.setId( null );
      todo.setUser( user );
      return ResponseEntity.ok( todoDao.save( todo ) );
   }

   @PatchMapping
   public ResponseEntity<Todo> patch( User user, @RequestBody Todo todo )
   {
      Todo existing = todoDao.findOneByIdAndUser( todo.getId(), user );
      if ( existing == null ) throw new EntityNotFoundException();

      existing.setDescription( todo.getDescription() );
      existing.setTitle( todo.getTitle() );
      existing.setDone( todo.isDone() );
      return ResponseEntity.ok( todoDao.save( existing ) );
   }

   @GetMapping("/{id}")
   public ResponseEntity<Todo> get( User user, @PathVariable("id") Integer id ) throws Exception
   {
      Todo todo = todoDao.findOneByIdAndUser( id, user );
      if ( todo == null ) throw new EntityNotFoundException();

      return ResponseEntity.ok( todo );
   }

   @GetMapping("/search")
   public ResponseEntity<PageableResult<Todo>> search
   (
      User user,
      @RequestParam String query,
      @RequestParam(defaultValue = "0") int offset,
      @RequestParam(defaultValue = "20") int limit,
      @RequestParam(defaultValue = "id") String sort,
      @RequestParam(defaultValue = "asc") String direction
   )
   {
      PageRequest pageable = new PageRequest(
        offset,
        limit,
        Sort.Direction.fromString( direction.toUpperCase() ),
        sort
      );

      List<Todo> todos = todoDao.search( user, query, query, pageable );

      long count = todoDao.searchCount( user, query, query );

      return ResponseEntity.ok( new PageableResult<>( todos, count ) );
   }

   @GetMapping
   public ResponseEntity<PageableResult<Todo>> list
   (
      User user,
      @RequestParam(defaultValue = "0") int offset,
      @RequestParam(defaultValue = "20") int limit,
      @RequestParam(defaultValue = "id") String sort,
      @RequestParam(defaultValue = "asc") String direction
   )
   {
      PageRequest pageable = new PageRequest(
        offset,
        limit,
        Sort.Direction.fromString( direction.toUpperCase() ),
        sort
      );

      List<Todo> todos = todoDao.findAllByUser( user, pageable );
      long count = todoDao.countByUser( user );

      return ResponseEntity.ok( new PageableResult<>( todos, count ) );
   }
}

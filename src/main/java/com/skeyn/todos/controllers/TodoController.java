package com.skeyn.todos.controllers;

import com.skeyn.todos.models.Todo;
import com.skeyn.todos.services.TodoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
public class TodoController {

  @Autowired
  TodoService todoService;

  @RequestMapping(value = "/todos", method = RequestMethod.GET)
  public ResponseEntity<List<Todo>> listAllTodos() {
    List<Todo> users = todoService.findAllTodos();
    if (users.isEmpty()) {
      return new ResponseEntity<>(
        HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<List<Todo>>(users, HttpStatus.OK);
  }

  @RequestMapping(value = "/todos/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Todo> getTodo(@PathVariable("id") long id) {
    Todo todo = todoService.findById(id);
    if (todo == null) {
      return new ResponseEntity<Todo>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<Todo>(todo, HttpStatus.OK);
  }

  @RequestMapping(value = "/todos", method = RequestMethod.POST)
  public ResponseEntity<Void> createTodo(@RequestBody Todo todo, UriComponentsBuilder ucBuilder) {
    if (todoService.isTodoPresent(todo)) {
      return new ResponseEntity<Void>(HttpStatus.CONFLICT);
    }

    todoService.saveTodo(todo);

    HttpHeaders headers = new HttpHeaders();
    headers.setLocation(ucBuilder.path("/todos/{id}").buildAndExpand(todo.getId()).toUri());
    return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
  }

  @RequestMapping(value = "/todos/{id}", method = RequestMethod.PUT)
  public ResponseEntity<Todo> updateTodo(@PathVariable("id") long id, @RequestBody Todo todo) {

    Todo currentTodo = todoService.findById(id);

    if (currentTodo == null) {
      return new ResponseEntity<Todo>(HttpStatus.NOT_FOUND);
    }

    currentTodo.setTitle(todo.getTitle());
    currentTodo.setDescription(todo.getDescription());

    todoService.updateTodo(currentTodo);
    return new ResponseEntity<Todo>(currentTodo, HttpStatus.OK);
  }

  @RequestMapping(value = "/todos/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<Todo> deleteTodo(@PathVariable("id") long id) {
    Todo todo = todoService.findById(id);
    if (todo == null) {
      return new ResponseEntity<Todo>(HttpStatus.NOT_FOUND);
    }

    todoService.deleteTodoById(id);
    return new ResponseEntity<Todo>(HttpStatus.NO_CONTENT);
  }
}
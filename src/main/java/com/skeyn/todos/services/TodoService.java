package com.skeyn.todos.services;

import com.skeyn.todos.models.Todo;
import java.util.List;

public interface TodoService {

  Todo findById(long id);

  void saveTodo(Todo todo);

  void updateTodo(Todo todo);

  void deleteTodoById(long id);

  List<Todo> findAllTodos();

  void deleteAllTodos();

  public boolean isTodoPresent(Todo todo);

}

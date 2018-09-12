package com.skeyn.todos.services;


import com.skeyn.todos.models.Todo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("todoService")
@Transactional
public class TodoServiceImpl implements TodoService {

  private static final AtomicLong counter = new AtomicLong();

  private static List<Todo> todos;

  static {
    todos = populateDummyTodos();
  }

  private static List<Todo> populateDummyTodos() {
    List<Todo> todos = new ArrayList<Todo>();
    todos.add(new Todo(counter.incrementAndGet(), "First Todo", "This is the first todo"));
    todos.add(new Todo(counter.incrementAndGet(), "Second Todo", "This is the second todo"));
    todos.add(new Todo(counter.incrementAndGet(), "Third Todo", "This is the third todo"));
    return todos;
  }

  public List<Todo> findAllTodos() {
    return todos;
  }

  public Todo findById(long id) {
    for (Todo todo : todos) {
      if (todo.getId() == id) {
        return todo;
      }
    }
    return null;
  }

  public Todo findByTitle(String title) {
    for (Todo todo : todos) {
      if (todo.getTitle().equalsIgnoreCase(title)) {
        return todo;
      }
    }
    return null;
  }

  public void saveTodo(Todo todo) {
    todo.setId(counter.incrementAndGet());
    todos.add(todo);
  }

  public void updateTodo(Todo todo) {
    int index = todos.indexOf(todo);
    todos.set(index, todo);
  }

  public void deleteTodoById(long id) {

    for (Iterator<Todo> iterator = todos.iterator(); iterator.hasNext(); ) {
      Todo todo = iterator.next();
      if (todo.getId() == id) {
        iterator.remove();
      }
    }
  }

  public boolean isTodoPresent(Todo todo) {
    return findByTitle(todo.getTitle()) != null;
  }

  public void deleteAllTodos() {
    todos.clear();
  }

}

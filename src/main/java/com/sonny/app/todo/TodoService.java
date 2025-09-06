package com.sonny.app.todo;

import com.sonny.app.todo.request.TodoRequest;
import com.sonny.app.todo.request.TodoUpdateRequest;
import com.sonny.app.todo.response.TodoResponse;

import java.util.List;

public interface TodoService {

    String createTodo(TodoRequest request, String userId);

    void updateTodo(TodoUpdateRequest request, String todoId, String userId);

    TodoResponse findTodoById(String todoId);

    List<TodoResponse> findAllTodosForToday(String userId);

    List<TodoResponse> findAllTodosByCategory(String categoryId, String userId);

    List<TodoResponse> findAllDueTodos(String userId);

    void deleteTodoById(String todoId);
}

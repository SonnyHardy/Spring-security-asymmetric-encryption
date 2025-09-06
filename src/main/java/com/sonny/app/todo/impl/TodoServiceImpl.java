package com.sonny.app.todo.impl;

import com.sonny.app.category.Category;
import com.sonny.app.category.CategoryRepository;
import com.sonny.app.todo.Todo;
import com.sonny.app.todo.TodoMapper;
import com.sonny.app.todo.TodoRepository;
import com.sonny.app.todo.TodoService;
import com.sonny.app.todo.request.TodoRequest;
import com.sonny.app.todo.request.TodoUpdateRequest;
import com.sonny.app.todo.response.TodoResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final TodoMapper todoMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public String createTodo(TodoRequest request, String userId) {
        final Category category = checkAndReturnCategory(request.getCategoryId(), userId);
        final Todo todo = this.todoMapper.toTodo(request);
        todo.setCategory(category);
        return this.todoRepository.save(todo).getId();
    }

    @Override
    public void updateTodo(TodoUpdateRequest request, String todoId, String userId) {
        final Todo todoToUpdate = this.todoRepository.findById(todoId)
                .orElseThrow(() -> new EntityNotFoundException("No todo found with id: " + todoId));
        final Category category = checkAndReturnCategory(request.getCategoryId(), userId);

        this.todoMapper.mergeTodo(todoToUpdate, request);
        todoToUpdate.setCategory(category);
        this.todoRepository.save(todoToUpdate);
    }

    @Override
    public TodoResponse findTodoById(String todoId) {
        return this.todoRepository.findById(todoId)
                .map(this.todoMapper::toTodoResponse)
                .orElseThrow(() -> new EntityNotFoundException("No todo found with id: " + todoId));
    }

    @Override
    public List<TodoResponse> findAllTodosForToday(String userId) {
        return this.todoRepository.findAllByUserId(userId)
                .stream()
                .map(this.todoMapper::toTodoResponse)
                .toList();
    }

    @Override
    public List<TodoResponse> findAllTodosByCategory(String categoryId, String userId) {
        return this.todoRepository.findAllByUserIdAndCategoryId(userId, categoryId)
                .stream()
                .map(this.todoMapper::toTodoResponse)
                .toList();
    }

    @Override
    public List<TodoResponse> findAllDueTodos(String userId) {
        return this.todoRepository.findAllDueTodos(userId)
                .stream()
                .map(this.todoMapper::toTodoResponse)
                .toList();
    }

    @Override
    public void deleteTodoById(String todoId) {
        this.todoRepository.deleteById(todoId);
    }

    private Category checkAndReturnCategory(String categoryId, String userId) {
        return this.categoryRepository.findByIdAndUserId(categoryId, userId)
                .orElseThrow(() -> new EntityNotFoundException("No category found for that user with id: " + categoryId));
    }
}

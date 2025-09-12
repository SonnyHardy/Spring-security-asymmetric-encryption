package com.sonny.app.todo;

import com.sonny.app.common.RestResponse;
import com.sonny.app.todo.request.TodoRequest;
import com.sonny.app.todo.request.TodoUpdateRequest;
import com.sonny.app.todo.response.TodoResponse;
import com.sonny.app.user.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/todos")
@RequiredArgsConstructor
@Tag(name = "Todos", description = "Todo API")
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<RestResponse> createTodo(
            @RequestBody @Valid
            final TodoRequest request,
            Authentication authentication
            ) {
        final String userId = ((User) authentication.getPrincipal()).getId();
        final String todoId = todoService.createTodo(request, userId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RestResponse(todoId));
    }

    @PutMapping("/{todo-id}")
    @PreAuthorize("@todoSecurityService.isTodoOwner(#todoId)")
    public ResponseEntity<RestResponse> updateTodo(
            @RequestBody @Valid
            final TodoUpdateRequest request,
            @PathVariable("todo-id")
            final String todoId,
            final  Authentication authentication
            ) {
        final String userId = ((User) authentication.getPrincipal()).getId();
        this.todoService.updateTodo(request, todoId, userId);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/{todo-id}")
    @PreAuthorize("@todoSecurityService.isTodoOwner(#todoId)")
    public ResponseEntity<TodoResponse> findTodoById(@PathVariable("todo-id") String todoId) {
        return  ResponseEntity.ok(todoService.findTodoById(todoId));
    }

    @GetMapping("/today")
    public ResponseEntity<List<TodoResponse>> findAllTodosByUserId(Authentication authentication) {
        final String userId = ((User) authentication.getPrincipal()).getId();
        return ResponseEntity.ok(this.todoService.findAllTodosForToday(userId));
    }

    @GetMapping("/category/{category-id}")
    @PreAuthorize("@categorySecurityService.isCategoryOwner(#categoryId)")
    public ResponseEntity<List<TodoResponse>> findAllTodosByCategory(
            @PathVariable("category-id")
            String categoryId,
            Authentication authentication
    ) {
        final String userId = ((User) authentication.getPrincipal()).getId();
        return ResponseEntity.ok(this.todoService.findAllTodosByCategory(categoryId, userId));
    }

    @GetMapping("/due")
    public ResponseEntity<List<TodoResponse>> findAllDueTodos(Authentication authentication) {
        final String userId = ((User) authentication.getPrincipal()).getId();
        return ResponseEntity.ok(this.todoService.findAllDueTodos(userId));
    }

    @DeleteMapping("/{todo-id}")
    @PreAuthorize("@todoSecurityService.isTodoOwner(#todoId)")
    public  ResponseEntity<RestResponse> deleteTodoById(@PathVariable("todo-id") String todoId) {
        this.todoService.deleteTodoById(todoId);
        return ResponseEntity.ok().build();
    }

}

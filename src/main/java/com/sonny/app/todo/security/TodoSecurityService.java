package com.sonny.app.todo.security;

import com.sonny.app.todo.Todo;
import com.sonny.app.todo.TodoRepository;
import com.sonny.app.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TodoSecurityService {

    private final TodoRepository todoRepository;

    public boolean isTodoOwner(String todoId) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final String userId = ((User) authentication.getPrincipal()).getId();

        final Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new EntityNotFoundException("No todo found with id: " + todoId));

        return todo.getUser().getId().equals(userId);
    }
}

package com.sukanta.springboottodo.services;

import com.sukanta.springboottodo.models.Todo;
import com.sukanta.springboottodo.repositories.TodoRepository;
import com.sukanta.springboottodo.repositories.userRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class todoServices {
    private final TodoRepository todoRepository;
    private final userRepository userRepository;

    public todoServices(TodoRepository todoRepository, userRepository userRepository) {
        this.todoRepository = todoRepository;
        this.userRepository = userRepository;
    }

    public Todo addTodo(@NotNull Todo request, String userId) {
        Todo todo = new Todo();

        todo.setTitle(request.getTitle());
        todo.setDescription(request.getDescription());
        todo.setCreatedBy(userId);
        todo.setCreatedAt(LocalDateTime.now());
        todo.setUpdatedAt(LocalDateTime.now());

        return todoRepository.save(todo);
    }

    public List<Todo> getTodos(String userId) {
        List<Todo> todos = todoRepository.findAllByCreatedBy(userId);

        for (Todo todo : todos) {
            userRepository.findById(todo.getCreatedBy()).ifPresent(todo::setUser);
        }

        return todos;
    }

    public Todo updateTodo(String todoId, Todo request, String userId) {
        Todo todo = todoRepository.findByIdAndCreatedBy(todoId, userId).orElse(null);

        if (todo == null) {
            return null;
        } else {
            todo.setTitle(request.getTitle());
            todo.setDescription(request.getDescription());

            return todoRepository.save(todo);
        }
    }

    public boolean deleteTodo(String todoId, String userId) {
        Todo todo = todoRepository.findByIdAndCreatedBy(todoId, userId).orElse(null);

        if (todo == null) {
            return false;
        } else {
            todoRepository.deleteByIdAndCreatedBy(todoId, userId);
            return true;
        }
    }

}

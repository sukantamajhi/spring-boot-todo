package com.sukanta.springboottodo.services;

import com.sukanta.springboottodo.models.Todo;
import com.sukanta.springboottodo.repositories.TodoRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class todoServices {
    private final Logger logger = LoggerFactory.getLogger(todoServices.class);


    private final TodoRepository todoRepository;

    public todoServices(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public Todo addTodo(Todo request, ObjectId userId) {
        logger.info(userId + " <<-- userId");

        Todo todo = new Todo(request.getTitle(), request.getDescription(), userId);
        return todoRepository.save(todo);
    }

    public List<Todo> getTodos() {
        return todoRepository.findAll();
    }

    public void updateTodo(String todoId, Todo request) {
        Optional<?> todo = todoRepository.findById(todoId);
        System.out.println(todo + " <<-- todo");

    }

}

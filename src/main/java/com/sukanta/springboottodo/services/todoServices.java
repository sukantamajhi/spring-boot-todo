package com.sukanta.springboottodo.services;

import com.sukanta.springboottodo.models.Todo;
import com.sukanta.springboottodo.repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class todoServices {

    @Autowired
    private TodoRepository todoRepository;

    public Todo addTodo(@RequestBody Todo request) {
        Todo todo = new Todo(request.getTitle(), request.getDescription(), request.getCreated_by());
        return todoRepository.save(todo);
    }

}

package com.sukanta.springboottodo.services;

import com.sukanta.springboottodo.models.Todo;
import com.sukanta.springboottodo.models.User;
import com.sukanta.springboottodo.repositories.TodoRepository;
import com.sukanta.springboottodo.repositories.userRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class todoServices {
    private final Logger logger = LoggerFactory.getLogger(todoServices.class);

    private final TodoRepository todoRepository;

    public todoServices(TodoRepository todoRepository, MongoTemplate mongoTemplate, MongoTemplate mongoTemplate1, userRepository userRepository) {
        this.todoRepository = todoRepository;
    }

    public Todo addTodo(Todo request, ObjectId userId) {
        Todo todo = new Todo(request.getTitle(), request.getDescription(), userId);
        todo.setCreated_at(LocalDateTime.now());
        todo.setUpdated_at(LocalDateTime.now());

        return todoRepository.save(todo);
    }

    public List<Todo> getTodos(ObjectId userId) {
        List<Todo> todos1 = todoRepository.findAll();

        for (Todo todo : todos1) {
            System.out.println(todo.getCreated_by() + "<<-- todos[i]");


            Todo todo1 = new Todo();

//            todo1.setCreatedBy();
        }

        return todoRepository.findAll();
    }

    public void updateTodo(String todoId, Todo request) {
        Optional<Todo> todo = todoRepository.findById(todoId);
        System.out.println(todo + " <<-- todo");
    }

}

package com.sukanta.springboottodo.repositories;

import com.sukanta.springboottodo.models.Todo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TodoRepository extends MongoRepository<Todo, String> {

}

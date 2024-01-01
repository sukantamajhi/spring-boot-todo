package com.sukanta.springboottodo.repositories;

import com.sukanta.springboottodo.models.Todo;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TodoRepository extends MongoRepository<Todo, String> {
}

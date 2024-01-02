package com.sukanta.springboottodo.repositories;

import com.sukanta.springboottodo.models.Todo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends MongoRepository<Todo, String> {

    List<Todo> findAllByCreatedBy(String createdBy);

    Optional<Todo> findByIdAndCreatedBy(String id, String User);

    void deleteByIdAndCreatedBy(String id, String User);

}
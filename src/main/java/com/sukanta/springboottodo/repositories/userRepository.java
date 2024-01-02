package com.sukanta.springboottodo.repositories;

import com.sukanta.springboottodo.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface userRepository extends MongoRepository<User, String> {

    User findByEmail(String email);

}

package com.sukanta.springboottodo.repositories;

import com.sukanta.springboottodo.models.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface userRepository extends MongoRepository<User, ObjectId> {

    User findByEmail(String email);

}

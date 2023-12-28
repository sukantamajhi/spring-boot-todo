package com.sukanta.springboottodo.models;

import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "users")
@Getter
public class User {

    @Field("email")
    @Indexed(unique = true)
    private final String email;
    @Field("name")
    private final String name;
    @Field("phone")
    private final String phone;
    @Field("password")
    private final String password;
    @Field("address")
    private final String address;
    @Id
    private ObjectId id;

    public User(String email, String name, String phone, String password, String address) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.address = address;
        this.id = ObjectId.get();
    }
}
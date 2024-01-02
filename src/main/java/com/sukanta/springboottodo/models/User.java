package com.sukanta.springboottodo.models;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "users")
@Getter
@Setter
public class User {
    @Id
    @NonNull
    private String id = String.valueOf(ObjectId.get());
    @Field("email")
    @Indexed(unique = true)
    private String email;
    @Field("name")
    private String name;
    @Field("phone")
    private String phone;
    @Field("password")
    private String password;
    @Field("address")
    private String address;

    public User(String email, String name, String phone, String password, String address) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.address = address;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
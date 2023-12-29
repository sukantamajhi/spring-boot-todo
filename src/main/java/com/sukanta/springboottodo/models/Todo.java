package com.sukanta.springboottodo.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.LocalDateTime;

@Document(collection = "todos")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class Todo implements Serializable {
    @Field(name = "title")
    private final String title;
    @Field(name = "description")
    private final String description;
    @Field(name = "created_by")
    @DBRef(db = "users")
    private final ObjectId created_by;
    @Field(name = "created_at")
    @CreatedDate
    private final LocalDateTime created_at;
    @Field(name = "updated_at")
    @LastModifiedDate
    private final LocalDateTime updated_at;


    public Todo(String title, String description, ObjectId created_by) {
        this.title = title;
        this.description = description;
        this.created_by = created_by;
        created_at = LocalDateTime.now();
        updated_at = LocalDateTime.now();
    }
}


package com.sukanta.springboottodo.models;

import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.Date;


@Getter
public class Todo {
    private final String title;
    private final String description;
    private final ObjectId created_by;
    private final Date created_at;
    private final Date updated_at;
    @Id
    private final ObjectId id;


    public Todo(String title, String description, ObjectId created_by) {
        this.title = title;
        this.description = description;
        this.created_by = created_by;
        this.created_at = new Date();
        this.updated_at = new Date();
        this.id = ObjectId.get();
    }
}


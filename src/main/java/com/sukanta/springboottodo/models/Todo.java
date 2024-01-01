package com.sukanta.springboottodo.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.NonNull;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.LocalDateTime;

@Document(collection = "todos")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class Todo implements Serializable {
    @Field(name = "title")
    @NonNull
    @Setter
    private String title;
    @Field(name = "description")
    @NonNull
    @Setter
    private String description;
    @Field(name = "created_by")
    @NonNull
    @Setter
    private ObjectId created_by;
    @Field(name = "created_at")
    @CreatedDate
    @Setter
    private LocalDateTime created_at;
    @Field(name = "updated_at")
    @LastModifiedDate
    @Setter
    private LocalDateTime updated_at;

    @Setter
    private User createdBy;


    public Todo() {

    }


    public Todo(@NotNull String title, @NotNull String description, @NotNull ObjectId created_by) {
        this.title = title;
        this.description = description;
        this.created_by = created_by;
    }

    public String getCreated_by() {
        return String.valueOf(created_by);
    }

    public ObjectId getCreatedBy(){
        return created_by;
    }
}

